package nl.hva.backend.services;

import dev.samstevens.totp.code.*;
import dev.samstevens.totp.exceptions.QrGenerationException;
import dev.samstevens.totp.qr.QrData;
import dev.samstevens.totp.qr.QrGenerator;
import dev.samstevens.totp.qr.ZxingPngQrGenerator;
import dev.samstevens.totp.recovery.RecoveryCodeGenerator;
import dev.samstevens.totp.secret.DefaultSecretGenerator;
import dev.samstevens.totp.secret.SecretGenerator;
import dev.samstevens.totp.time.SystemTimeProvider;
import dev.samstevens.totp.time.TimeProvider;
import nl.hva.backend.models.User.User;
import org.springframework.stereotype.Service;

import static dev.samstevens.totp.util.Utils.getDataUriForImage;

/**
 * TwoFactorService
 * To authenticate a user with 2FA,
 * we need to generate a secret key for the user and generate a QR code for the user to scan.
 * @author Pepijn dik
 * @since 03/10/2022
 */
@Service
public class TwoFactorService {

    /**
     * Generate a secret key for the user twofactor authentication
     *
     * @return String Secret key
     */
    public static String generateSecretKey() {
        SecretGenerator secretGenerator = new DefaultSecretGenerator();
        return secretGenerator.generate();
    }

    /**
     * Generate a QR image url for the user to scan
     * @param qrData
     * @return
     */
    public static String generateQRUrl(QrData qrData) throws QrGenerationException {
        QrGenerator generator = new ZxingPngQrGenerator();
        String dataUri = "";
        try{
            byte[] imageData = generator.generate(qrData);
            String mimeType = generator.getImageMimeType();
            dataUri = getDataUriForImage(imageData, mimeType);
        }catch (Exception e){
            System.out.println(e);
        }
        return dataUri;
    }


    /**
     * Generate a QR Data for the user twofactor authentication
     *
     * @param secret
     * @param user
     * @return QrData
     */
    public static QrData generateQRData(String secret, User user) {
        return new QrData.Builder()
                .label(user.getName())
                .secret(secret)
                .issuer("Sumting admin panel")
                .algorithm(HashingAlgorithm.SHA1) // More on this below
                .digits(6)
                .period(30)
                .build();
    }

    /**
     * Verify the secret code the user entered
     * @param secret
     * @param code
     * @return
     */
    public static boolean VerifyOneTimePasswordResponse(String secret, String code) {
        TimeProvider timeProvider = new SystemTimeProvider();
        CodeGenerator codeGenerator = new DefaultCodeGenerator();
        CodeVerifier verifier = new DefaultCodeVerifier(codeGenerator, timeProvider);
        // secret = the shared secret for the user
        // code = the code submitted by the user
       return verifier.isValidCode(secret, code);
    }

    public static String[] GenerateRecoveryCode(){
        // Generate 16 random recovery codes
        RecoveryCodeGenerator recoveryCodes = new RecoveryCodeGenerator();
        return recoveryCodes.generateCodes(16);
    }
}
