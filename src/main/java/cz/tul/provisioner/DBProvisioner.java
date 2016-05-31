package cz.tul.provisioner;

import cz.tul.data.Autor;
import cz.tul.data.Images;
import cz.tul.repositories.BaseAutorRepository;
import cz.tul.repositories.BaseCommentRepository;
import cz.tul.repositories.BaseImagesRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
@Transactional
public class DBProvisioner implements InitializingBean {
    @Autowired
    BaseImagesRepository baseImagesRepository;
    @Autowired
    BaseCommentRepository baseCommentRepository;
    @Autowired
    BaseAutorRepository baseAutorRepository;


    public void afterPropertiesSet() throws Exception {
        init();

    }

    private boolean init() throws IOException {

        Autor autor = new Autor(UUID.randomUUID(), "Marek", new Date(System.currentTimeMillis()));
        Images images1 = new Images(UUID.randomUUID(), "http://dirk.kmi.tul.cz/faculty/logo/TUL.png", "test", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis() + 1000), autor);
        Images images2 = new Images(UUID.randomUUID(), "http://img7.rajce.idnes.cz/d0703/0/469/469473_c83cd82fcc6814eb037e5b01f49ba246/images/sporic_Bomba.jpg", "test22", new Date(System.currentTimeMillis() + 2000), new Date(System.currentTimeMillis()), autor);
        Images images3 = new Images(UUID.randomUUID(), "http://img8.rajce.idnes.cz/d0802/0/470/470054_ec2a8356210bb9129c6a15abff1a2572/images/Lebka_cernobila.JPG", "test33", new Date(System.currentTimeMillis() + 3000), new Date(System.currentTimeMillis()), autor);
        Images images4 = new Images(UUID.randomUUID(), "/img/logo.png", "test44", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), autor);

        images2.setTags("ahoj|test|neco");
        baseAutorRepository.save(autor);
        baseImagesRepository.save(images1);
        baseImagesRepository.save(images2);
        baseImagesRepository.save(images3);
        baseImagesRepository.save(images4);


        return false;
    }


}
