package main

import "C"
import (
	"crypto/rand"
	"encoding/hex"
	"fmt"
	"github.com/smartcontractkit/chainlink/core/services/signatures/secp256k1"
	"github.com/smartcontractkit/chainlink/core/services/vrf"
	"math/big"
)

func main() {

}

//export VRFProoFGenerate
func VRFProoFGenerate(sk *C.char, preseed *C.char) *C.char {
	nonce, err := rand.Int(rand.Reader, secp256k1.GroupOrder)
	if err != nil {
		fmt.Print(err)
	}
	sk1 := C.GoString(sk)
	rawSecretKey := new(big.Int)
	rawSecretKey, ok := rawSecretKey.SetString(sk1, 16)
	if !ok {
		fmt.Println("SetString: error")

	}

	preseed1 := C.GoString(preseed)
	seed := new(big.Int)
	seed, ok1 := seed.SetString(preseed1, 16)
	if !ok1 {
		fmt.Println("SetString: error")

	}
	proofBlob, err := vrf.GenerateProofWithNonce(rawSecretKey, seed,nonce ) /* nonce */
	fmt.Println(err)

	p2,err := proofBlob.MarshalForSolidityVerifier()

	//p.Seed = big.NewInt(0).Add(seed, big.NewInt(1))
	valid, err1 := proofBlob.VerifyVRFProof()
	fmt.Println(p2)
	fmt.Println(valid)
	fmt.Println(err1)
	hexstring := hex.EncodeToString( p2[:])
	fmt.Println(hexstring)

	return C.CString(hexstring)
}



//const ProofLength = 64 + // PublicKey
//	64 + // Gamma
//	32 + // C
//	32 + // S
//	32 + // Seed
//	32 + // uWitness (gets padded to 256 bits, even though it's only 160)
//	64 + // cGammaWitness
//	64 + // sHashWitness
//	32 // zInv  (Leave Output out, because that can be efficiently calculated)




