package model.security;

import java.security.PublicKey;

public interface ISecurityManager {
	public byte[] sign(byte[] toSign);

	public boolean verify(PublicKey pubKey, byte[] toUpdate, byte[] toVerify);
	
	public PublicKey getPublicKey();
}
