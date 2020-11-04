# Oracle-Service
   
###1 原理简介：
   
   ####1.1 原理
   区块链是一个确定性的、封闭的系统环境，目前区块链只能获取到链内的数据，而不能获取到链外真实世界的数据，区块链与现实世界是割裂的。
   
   区块链是确定性的环境，它不允许不确定的事情或因素，智能合约不管何时何地运行都必须是一致的结果，所以虚拟机（VM）不能让智能合约有 network call（网络调用），不然结果就是不确定的。
   当智能合约的触发条件是外部信息时（链外），就必须需要预言机来提供数据服务，通过预言机将现实世界的数据输入到区块链上，因为智能合约不支持对外请求。
   也就是说智能合约不能进行 I/O（Input/Output，即输入/输出），所以它是无法主动获取外部数据的，只能通过预言机将数据给到智能合约。
   
   Oracle-Service是fisco链上的预言机服务。此服务作用是负责取相关用户指定的url的数据，并回写到用户的合约上。方便用户在链上访问链下数据。
   并支持连接多链多群组，可同时为不同链和群组提供oracle服务。
   
   Oracle-Service 服务需配合OracleCore（在项目的contracts目录下）合约使用。 服务启动时会部署OracleCore合约或者加载OracleCore合约（如果已配置合约地址），然后监听此合约的事件。
   如图![oracle流程图](./oracle.png)
   
   用户只需要参考SampleOracle.sol合约，部署此合约并调用oracle_setNetwork方法并传入oracleCore合约地址，这样Oracle-Service可以回写结果到用户的SampleOracle合约。用户就可以调用自己的SampleOracle合约查询到链下的数据。
   
   并支持国密，以及请求状态查询。
         

   
   ####1.2 设计方案

   ####1.3 快速开发
   
   
###2 功能
   ####2.1 API数据获取
   支持HTTPS的接口访问。
   ####2.2 VRF随机数生成
   
  采用k1椭圆曲线的VRF算法。链上合约验证Proof。
  用户提供随机数种子，oracle service服务方提供自己私钥，产生VRF的 proof。
  链上合约验证proof。    
   [VRF介绍](./VRF.md)
   ####2.3 去中心化数据获取（聚合）
   支持用户选择多个oracle service帮获取数据。并进行聚合，最终返回给用户合约。
 
   
   

###3 [安装部署](./install.md)  

###4 使用注意事项：
 1 用户可以参考contracts/RandomOracle.sol合约实现自己的oracle合约。 首先必须集成usingOracleCore合约，并且实现__callback方法,此方法供oracle-service服务回调你的合约，将查询结果写到你的合约里。
  其次在update方法中填入自己要访问的链下API的URL。
 2 目前支持json和text/plain两种访问格式。并且链下API的url必须支持HTTPS访问。
 - plain(https://www.random.org/integers/?num=100&min=1&max=100&col=1&base=10&format=plain&rnd=new)
 - json(https://api.exchangerate-api.com/v4/latest/CNY).rates.JPY
  

