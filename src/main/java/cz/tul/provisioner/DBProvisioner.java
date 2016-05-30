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


//TODO
        Autor autor = new Autor("" + ObjectId.get(), "Marek", new Date(System.currentTimeMillis()));
        Images images1 = new Images("" + ObjectId.get(),"http://dirk.kmi.tul.cz/faculty/logo/TUL.png", "test", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), autor,0);
        Images images2 = new Images("" + ObjectId.get(),"http://img7.rajce.idnes.cz/d0703/0/469/469473_c83cd82fcc6814eb037e5b01f49ba246/images/sporic_Bomba.jpg", "test22", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), autor,1);
        Images images3 = new Images("" + ObjectId.get(),"http://img8.rajce.idnes.cz/d0802/0/470/470054_ec2a8356210bb9129c6a15abff1a2572/images/Lebka_cernobila.JPG", "test33", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), autor,2);
        Images images4 = new Images("" + ObjectId.get(),"/img/logo.png", "test33", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), autor,3);

    images2.setTagses("ahoj test neco");
        baseAutorRepository.save(autor);
        baseImagesRepository.save(images1);
        baseImagesRepository.save(images2);
        baseImagesRepository.save(images3);
        baseImagesRepository.save(images4);


        return false;
    }





}
