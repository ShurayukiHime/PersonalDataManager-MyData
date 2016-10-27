package persistence;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;

//classe random per pulire il consent manager

// tutte le eccezioni sono state catturate perchè boh

// manca in toto il provider

public class SecurityManager implements ISecurityManager {

	private KeyPair keyPair;

	private final String keyAlgorithm = "DSA";
	private final String randomAlgorithm = "SHA1PRNG";
	private final String provider = "SUN";
	private final String signatureAlgorithm = "SHA1withDSA";

	public SecurityManager() {
		try {
			this.keyPair = generateKeys(keyAlgorithm, randomAlgorithm);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private KeyPair generateKeys(String keyAlgorithm, String randomAlgorithm) throws NoSuchAlgorithmException {
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(keyAlgorithm);
		SecureRandom random = null;
		try {
			random = SecureRandom.getInstance(randomAlgorithm, provider);
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
			throw new SecurityException("Unexpected error during keys generation.");
		}
		// 1024 e gli altri dati vanno bene?
		keyPairGen.initialize(1024, random);
		return keyPairGen.generateKeyPair();
	}

	public byte[] sign(byte[] toSign) {

		// questo algoritmo è solo uno scheletro
		// manca la lettura da file
		// l'update dei bytes non ha molto senso fatto così

		Signature dsa = null;
			byte realSig[] = null;
			try {
				dsa = Signature.getInstance(signatureAlgorithm);
				PrivateKey privKey = keyPair.getPrivate();
				dsa.initSign(privKey);
				realSig = null;
				dsa.update(toSign);
				realSig = dsa.sign();
			} catch (InvalidKeyException | NoSuchAlgorithmException | SignatureException e) {
				e.printStackTrace();
				throw new SecurityException("Error encountered during sign operation.");
			}
		return realSig;
	}

	public boolean verify(PublicKey pubKey, byte[] toUpdate, byte[] toVerify) {
		// questo algoritmo è solo uno scheletro
		// manca la lettura da file
		// l'update dei bytes non ha molto senso fatto così

		Signature dsa = null;
		boolean result;
		try {
			dsa = Signature.getInstance(signatureAlgorithm);
			result = false;
			dsa.initVerify(pubKey);
			dsa.update(toUpdate);
			result = dsa.verify(toVerify);
		} catch (InvalidKeyException | NoSuchAlgorithmException | SignatureException e) {
			e.printStackTrace();
			throw new SecurityException("Error encountered during verify operation.");
		}
		return result;
	}

	
	@Override
	public PublicKey getPublicKey() {
		return this.keyPair.getPublic();
	}
}
