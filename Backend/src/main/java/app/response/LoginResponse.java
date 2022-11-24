package app.response;

import app.models.User.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginResponse
{
    @JsonProperty("me")
    private User me;
    private String status;
    @JsonProperty("message")
    private String message;
    @JsonProperty("need_twofactor")
    private boolean needTwoFactor;
    public LoginResponse()
    {

    }

    public void setMe(User me) {
        this.me = me;
    }
    public void setNeedTwoFactor(boolean needTwoFactor) {
        this.needTwoFactor = needTwoFactor;
        if(this.needTwoFactor){
            this.me = null;
        }
    }
}
