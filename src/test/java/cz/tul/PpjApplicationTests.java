package cz.tul;

import cz.tul.client.ServerApi;
import cz.tul.data.Autor;
import cz.tul.data.Images;
import cz.tul.repositories.BaseAutorRepository;
import cz.tul.repositories.BaseCommentRepository;
import cz.tul.repositories.BaseImagesRepository;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import retrofit.RestAdapter;


import java.util.Date;

import java.util.UUID;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PpjApplication.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class PpjApplicationTests {
	@Autowired
	BaseImagesRepository baseImagesRepository;
	@Autowired
	BaseCommentRepository baseCommentRepository;
	@Autowired
	BaseAutorRepository baseAutorRepository;


	@Test
	public void contextLoads() {
	}
	Autor autor = new Autor(UUID.randomUUID(), "ClassTest Autor", new Date(System.currentTimeMillis()));
	Images insert = new Images( UUID.randomUUID(),"test","testovaci", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), autor);
	Images insert2 = new Images( UUID.randomUUID(),"test","pokus", new Date(System.currentTimeMillis()+1000), new Date(System.currentTimeMillis()+1000), autor);

	@Before
	public void init(){
		insert.setTags("ahoj cus neco");
		baseAutorRepository.save(autor);
		baseImagesRepository.save(insert);

	}
	@Test
	public void testExists() {
		boolean exist =false;

		String name = baseAutorRepository.findByName("ClassTest Autor").getName();
		if(name.equals("ClassTest Autor")){
			exist=true;
		}
		assertTrue("Nejsou stejní", exist);

	}
	@Test
	public void testFindByAutor(){

		boolean exist =false;
		long count = baseImagesRepository.countByAutor(autor);
		if(count >0){exist=true;}
		assertTrue("Nenalezen žádný obrázek podle autora", exist);

	}

	@Test
	public void testFingByTag(){
		boolean exist =false;
		Images list = baseImagesRepository.findByTagsLike("neco");
		if(list !=null){exist=true;}
		assertTrue("Nenalezen žádný obrázek podle tagu", exist);
	}

}
