package com.project.back_end;
import com.project.back_end.services.TokenService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class TokenServiceTests {
 @Test void generatedTokenContainsEmail(){TokenService tokens=new TokenService("01234567890123456789012345678901",3600000);String token=tokens.generateToken("test@example.com");assertEquals("test@example.com",tokens.email(token));assertEquals("PATIENT",tokens.role(token));}
}
