#!/usr/bin/env bash

# 命令返回非 0 时，就退出
set -o errexit
# 管道命令中任何一个失败，就退出
set -o pipefail
# 遇到不存在的变量就会报错，并停止执行
set -o nounset
# 在执行每一个命令之前把经过变量展开之后的命令打印出来，调试时很有用
#set -o xtrace

# 退出时，执行的命令，做一些收尾工作
trap 'echo -e "Aborted, error $? in command: $BASH_COMMAND"; trap ERR; exit 1' ERR

# Set magic variables for current file & dir
# 脚本所在的目录
__dir="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
# 脚本的全路径，包含脚本文件名
__file="${__dir}/$(basename "${BASH_SOURCE[0]}")"
# 脚本的名称，不包含扩展名
__base="$(basename ${__file} .sh)"
# 脚本所在的目录的父目录，一般脚本都会在父项目中的子目录，
#     比如: bin, script 等，需要根据场景修改
#__root="$(cd "$(dirname "${__dir}")" && pwd)" # <-- change this as it depends on your app
__root="${__dir}" # <-- change this as it depends on your app

LOG_WARN() {
    local content=${1}
    echo -e "\033[31m[WARN] ${content}\033[0m"
}

LOG_INFO() {
    local content=${1}
    echo -e "\033[32m[INFO] ${content}\033[0m"
}

## replace env with value in file
function replace_vars_in_file(){
    file="$1"

    content=$(cat "${file}" | envsubst)
    cat <<< "${content}" > "${file}"
}

## install app
function install(){
    # 系统命令
    command=$1
    # 安装的应用名，如果系统命令不存在，则安装
    app_name=$2

    # install app
    if [[ ! $(command -v "${command}") ]] ;then
        if [[ $(command -v apt) ]]; then
            # Debian/Ubuntu
            echo "Start to check and install ${app_name} on remote Debian system ..."
            sudo dpkg -l | grep -qw "${app_name}" || sudo apt install -y "${app_name}"
        elif [[ $(command -v yum) ]]; then
            ## RHEL/CentOS
            echo "Start to check and install ${app_name} on remote RHEL system ..."
            sudo rpm -qa | grep -qw "${app_name}" || sudo yum install -y "${app_name}"
        fi
    fi
}

## check and pull docker image
CDN_BASE_URL="https://osp-1257653870.cos.ap-guangzhou.myqcloud.com/WeBASE/download/docker/image"
function pull_image(){
    # 镜像名和版本
    repository=$1
    tag=$2
    tar_file_name=$3

    tar_file="${tar_file_name}-${tag}.tar"

    if [[ "$(docker images -q ${repository}:${tag} 2> /dev/null)" == "" ]]; then
        LOG_WARN "Docker image [${repository}:${tag}] not exists!!"
        echo ""
        LOG_INFO "Pull image [${repository}:${tag}] from ${image_from}!!"
        case ${image_from} in
            cdn )
                wget "${CDN_BASE_URL}/${tar_file}" -O ${tar_file} && docker load -i ${tar_file} && rm -rf ${tar_file}
                ;;
            docker )
                docker pull ${repository}:${tag}
                ;;
            *)
            LOG_WARN "Option '-t' has only two available values: 'cdn' or 'docker'"
            usage
            exit 1;
        esac
    else
        LOG_INFO "Docker image [${repository}:${tag}] exists."
    fi


    if [[ "$(docker images -q ${repository}:${tag} 2> /dev/null)" == "" ]]; then
        LOG_WARN "Docker image:[${repository}:${tag}] is still missing after pull execution !!"
        echo ""
        LOG_WARN "Please check the network and try to re-run deploy.sh with '-c cdn' parameter."
        exit 5;
    fi
}


## check command
function check_commands(){
    for i in "$@"; do
        if [[ ! $(command -v $i) ]]; then
            LOG_WARN "[$i] not found, please install [$i]."
            exit 5
        fi
    done
}

## check port is listening
function check_port(){
    port=$1
    service_name=$2

    process_of_port=$(lsof -i -P -n | grep LISTEN | grep -w ":${port}") || :
    if [[ "${process_of_port}x" != "x" ]]; then
        process_name=$(echo ${process_of_port} | awk '{print $1}')
        process_id=$(echo ${process_of_port} | awk '{print $2}')
        LOG_WARN "Port:[${port}] is already in use by a process ${process_id} of [${process_name}], please leave the port:[${port}] for service:[${service_name}]"
        exit 5
    fi
}

## 获取用户输入
# $1 提示信息
# $2 使用正则校验用户的输入
# $3 默认值
read_value=""
function read_input(){
    read_value=""

    tip_msg="$1"
    regex_of_value="$2"
    default_value="$3"

    until  [[ ${read_value} =~ ${regex_of_value} ]];do
        read -r -p "${tip_msg}" read_value;read_value=${read_value:-${default_value}}
    done
}


####### 参数解析 #######
cmdname=$(basename $0)
image_organization=fiscoorg
fiscobcos_version="v2.6.0"
webase_front_version="v1.4.2"
weoracle_version="v0.4"
guomi="no"
install_deps="no"
# 拉取镜像的方式，cdn、Docker Hub，默认：cdn
image_from="cdn"


# usage help doc.
usage() {
    cat << USAGE  >&2
Usage:
    $cmdname [-g] [-t cdn|docker] [-d] [-w v1.4.2] [-f v2.6.0] [-o v0.4] [-i fiscoorg] [-h]
    -g        Use guomi, default no.
    -t        Where to get docker images, cdn or Docker hub, default cdn.
    -d        Install dependencies during deployment, default no.

    -w        WeBASE-Front version, default v1.4.2
    -f        FISCO-BCOS version, default v2.6.0.
    -o        WeOracle version, default v0.4.
    -i        Organization of docker images, default fiscoorg.
    -h        Show help info.
USAGE
    exit 1
}

while getopts gt:dw:f:o:i:h OPT;do
    case $OPT in
        g)
            guomi="yes"
            ;;
        t)
            case $OPTARG in
                cdn | docker )
                    ;;
                *)
                LOG_WARN "Invalid value of '-t' parameter, valid are [cdn] or [docker]!"
                    usage
                    exit 1;
            esac
            env=$OPTARG
            ;;
        d)
            install_deps="yes"
            ;;
        w)
            webase_front_version=$OPTARG
            ;;
        f)
            fiscobcos_version=$OPTARG
            ;;
        o)
            weoracle_version=$OPTARG
            ;;
        i)
            image_organization=$OPTARG
            ;;
        h)
            usage
            exit 3
            ;;
        \?)
            usage
            exit 4
            ;;
    esac
done

################### install deps if with -d option ###################
echo "=============================================================="
## install dependency software
if [[ "${install_deps}x" == "yesx" ]]; then
    LOG_INFO "Start to install dependencies ..."

    #TODO. check system version

    install openssl openssl
    install wget    wget
    install curl    curl
    
    ## install docker
    if [[ ! $(command -v docker) ]]; then
        LOG_INFO "Installing Docker ..."
        curl -fsSL https://get.docker.com | bash -s docker --mirror Aliyun;
    fi
    ## install docker-compose
    if [[ ! $(command -v docker-compose) ]]; then
        LOG_INFO "Installing Docker Compose ..."
        ## TODO. fetch latest tag
        ## curl -L "https://github.com/docker/compose/releases/download/1.27.4/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose;
        curl -L https://get.daocloud.io/docker/compose/releases/download/1.27.4/docker-compose-`uname -s`-`uname -m` -o /usr/local/bin/docker-compose
        chmod +x /usr/local/bin/docker-compose;
    fi
fi


# check if deps are installed
LOG_INFO "Start to check dependencies ..."
check_commands curl wget openssl docker docker-compose

# start docker
LOG_INFO "Start Docker service ..."
systemctl start docker

echo "=============================================================="
################### check if ports available ###################
LOG_INFO "Start to check ports are available ..."
# check MySQL
check_port 3306 MySQL

# check WeBASE-Front
check_port 5002 "WeBASE-Front"

# check WeOracle-Web
check_port 5000 "WeOracle-Web"

# check WeOracle-Service
check_port 5012 "WeOracle-Service"

# check p2p port
check_port 30300 "node0 p2p"
check_port 30301 "node1 p2p"
check_port 30302 "node2 p2p"
check_port 30303 "node3 p2p"

# check channel port
check_port 20200 "node0 channel"
check_port 20201 "node1 channel"
check_port 20202 "node2 channel"
check_port 20203 "node3 channel"

# check JSON-RPC port
check_port 8545 "node0 JSON-RPC"
check_port 8546 "node1 JSON-RPC"
check_port 8547 "node2 JSON-RPC"
check_port 8548 "node3 JSON-RPC"


echo "=============================================================="
################### fetch latest build_chain.sh ###################
fisco_bcos_root="${__root}/fiscobcos"
build_chain_shell="build_chain.sh"

# cd to fiscobcos dir
cd "${fisco_bcos_root}"

# download build_chain.sh
# TODO. check MD5 of build_chain.sh
if [[ ! -f "${build_chain_shell}" ]]; then
    LOG_INFO "Downloading build_chain.sh ..."
    curl -#L https://gitee.com/FISCO-BCOS/FISCO-BCOS/raw/master/tools/build_chain.sh > "${build_chain_shell}" && chmod u+x "${build_chain_shell}"
fi

################### generate nodes config ###################
if [[ -d "nodes" ]]; then
    LOG_WARN "Nodes directory:[${fisco_bcos_root}/nodes] exists, BACKUP:[b] or DELETE:[d]?"
    # 调用 readValue
    # 大小写转换
    read_input "BACKUP(b), DELETE(d)? [b/d], 默认: b ? " "^([Bb]|[Dd])$" "b"
    delete_nodes=$(echo "${read_value}" | tr [A-Z]  [a-z])

    if [[ "${delete_nodes}x" == "dx" ]]; then
        read_input "Confirm to delete directory:[${fisco_bcos_root}/nodes]. (y/n), 默认: n ? " "^([Yy]|[Nn])$" "n"
        confirm_delete_nodes=$(echo "${read_value}" | tr [A-Z]  [a-z])
        if [[ "${confirm_delete_nodes}x" != "yx" ]]; then
            delete_nodes="b"
        fi
    fi

    ## backup or delete
    case ${delete_nodes} in
     d)
        LOG_WARN "Delete directory:[${fisco_bcos_root}/nodes] ..."
        rm -rfv nodes
        ;;
     b)
        new_node_dir=nodes-$(date "+%Y%m%d-%H%M%S")
        LOG_INFO "Backup directory:[${fisco_bcos_root}/nodes] to [${fisco_bcos_root}/${new_node_dir}]..."
        mv -fv nodes ${new_node_dir}
        ;;
     *)
        echo "Unknown operation type : [${OPT_TYPE}]"
        exit 1
    esac
fi

guomi_opt=""
if [[ "${guomi}x" == "yesx" ]]; then
    guomi_opt=" -g "
fi

LOG_INFO "Generate FISCO-BCOS nodes' config ..."
bash ${build_chain_shell} -l "127.0.0.1:4" -d "${guomi_opt}"


echo "=============================================================="
################### check images ###################
echo ""
LOG_INFO "Check docker images exist ..."
mysql_repository="mysql"
fiscobcos_repository="fiscoorg/fiscobcos"
weoracle_service_repository="${image_organization}/weoracle-service"
weoracle_web_repository="${image_organization}/weoracle-web"
webase_front_repository="${image_organization}/webase-front"
mysql_version=5.7

pull_image ${mysql_repository} ${mysql_version} "mysql"
pull_image ${fiscobcos_repository} ${fiscobcos_version} "fiscobcos"
pull_image ${weoracle_web_repository} ${weoracle_version} "weoracle-service"
pull_image ${weoracle_service_repository} ${weoracle_version} "weoracle-web"
pull_image ${webase_front_repository} ${webase_front_version} "webase-front"


# guomi option
encrypt_type="0"
if [[ "${guomi}x" == "yesx" ]]; then
    encrypt_type="1"
fi

export image_organization

echo "=============================================================="
################### update webase files ###################
echo ""
LOG_INFO "Replace encrypt_type in webase-front.yml file..."
sed -i "s/encryptType.*#/encryptType: ${encrypt_type} #/g" ${__root}/webase/webase-front.yml

LOG_INFO "Replace WeBASE-Front version in docker-compose.yml file..."
export webase_front_version
replace_vars_in_file ${__root}/webase/docker-compose.yml


################### update fiscobcos files ###################
LOG_INFO "Replace FISCO-BCOS version in node.yml file..."
export fiscobcos_version
replace_vars_in_file ${__root}/fiscobcos/node.yml

################### update WeOracle files ###################
LOG_INFO "Replace WeOracle weoracle.yml file..."
export weoracle_version
export mysql_version
replace_vars_in_file ${__root}/weoracle/docker-compose.yml
sed -i "s/encryptType.*#/encryptType: ${encrypt_type} #/g" ${__root}/weoracle/weoracle.yml

echo "=============================================================="
LOG_INFO "Deploy WeOracle service SUCCESS!!"
echo ""
LOG_INFO "  Start:[ bash start.sh ]"
echo ""
LOG_INFO "  Stop :[ bash stop.sh  ]"

