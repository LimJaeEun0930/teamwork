package capweb.capprac.util;

import capweb.capprac.dto.USerCreateFormData;
import capweb.capprac.entity.USer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateUSer {
    public USer CreateUSer(USerCreateFormData userFormData) {
        USer user = new USer();
        user.setUsId(userFormData.getUsId());
        user.setUsPw(userFormData.getUsPw());
        user.setUsName(userFormData.getUsName());
        return user;
    }
}
