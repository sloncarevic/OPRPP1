package hr.fer.oprpp1.hw05.crypto;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Class for executing digesting, encrypting and decrypting
 *
 */
public class Crypto {

	/**
	 * @param args
	 * @throws IllegalArgumentException
	 */
	public static void main(String[] args) {
		if (!(args.length == 2 || args.length == 3)) throw new IllegalArgumentException("Invalid number of arguments!");
		
		Scanner sc = null;
		try {
			sc = new Scanner(System.in);
			
			if (args[0].equals("checksha")) {
				if (args.length != 2) throw new IllegalArgumentException("Invalid number of arguments!");
				if (!args[1].endsWith(".bin")) throw new IllegalArgumentException("Invalid file extension!");
				
				System.out.print("Please provide expected sha-256 digest for" + args[1] +":\n> ");
				String expectedDigest = sc.nextLine().strip();
				
				
				MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
				
				try {
					InputStream inputStream = new BufferedInputStream(Files.newInputStream(Path.of(args[1])));
					
					byte[] bufferArr = new byte[4096];
					
					while (true) {
						int numOfBytes = inputStream.read(bufferArr);
						if (numOfBytes == -1) break;
						messageDigest.update(bufferArr, 0, numOfBytes);
						
					}
					String resultDigest = Util.bytetohex(messageDigest.digest());
					
					inputStream.close();
					
					System.out.print("Digesting completed. ");
					if (expectedDigest.equals(resultDigest))
						System.out.println("Digest of " + args[1] + " matches expected digest.");
					else
						System.out.println("Digest of " + args[1] + " does not match the expected digest. Digest was: " + resultDigest);
					
					
				} catch (IOException e) {
					System.err.println(e.getMessage());
				}
				
				
			} 
			else if (args[0].equals("encrypt") || args[0].equals("decrypt")) {
				if (args.length != 3) throw new IllegalArgumentException("Invalid number of arguments!");
				if (!(args[1].endsWith(".bin") || args[1].endsWith(".pdf"))) throw new IllegalArgumentException("Invalid file extension!");
				if (!args[2].endsWith(".pdf")) throw new IllegalArgumentException("Invalid file extension!");
				
				System.out.print("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):\n> ");

				String keyText = sc.nextLine().trim();
				if (keyText.length() != 32) throw new IllegalArgumentException("Incorrect password!");
				
				System.out.print("Please provide initialization vector as hex-encoded text (32 hex-digits):\n> ");

				String ivText = sc.nextLine().trim();
				if (ivText.length() != 32) throw new IllegalArgumentException("Incorrect initialization vector!");
				
				SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(keyText), "AES");
				AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(ivText));
				
				Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
				
				boolean encrypt = false;
				if(args[0].equalsIgnoreCase("encrypt")) encrypt = true;
				
				cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);
				
				try {
					InputStream inputStream = new BufferedInputStream(Files.newInputStream(Path.of(args[1])));
					OutputStream outputStream = new BufferedOutputStream(Files.newOutputStream(Path.of(args[2])));
					
					byte[] bufferArr = new byte[4096];
					
					while (true) {
						int numOfBytes = inputStream.read(bufferArr);
						if (numOfBytes == -1) {
							outputStream.write(cipher.doFinal());
							break;
						}
						outputStream.write(cipher.update(bufferArr, 0, numOfBytes));
					}
					inputStream.close();
					outputStream.close();

					if (encrypt) {
						System.out.println("Encryption completed. Generated file " + args[2] + " based on file " + args[1] + ".");
					} else {
						System.out.println("Decryption completed. Generated file " + args[2] + " based on file " + args[1] + ".");
					}
					
				} catch (IllegalBlockSizeException | BadPaddingException | IOException e) {
					System.err.println(e.getClass() + ": " + e.getMessage());
				}
				
			}
			else {
				throw new IllegalArgumentException("Invalid command!");
			}
			
		} catch (IllegalArgumentException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidAlgorithmParameterException | InvalidKeyException e ) {
			System.err.println(e.getMessage());
		} finally {
			sc.close();
		}
		
		
	}
}
