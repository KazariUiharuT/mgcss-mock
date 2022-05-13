package com.acme.bnb;

import com.acme.bnb.model.Actor;
import com.acme.bnb.model.Administrator;
import com.acme.bnb.model.Audit;
import com.acme.bnb.model.AuditAttachment;
import com.acme.bnb.model.Auditor;
import com.acme.bnb.model.Comment;
import com.acme.bnb.model.Commentable;
import com.acme.bnb.model.Lessor;
import com.acme.bnb.model.Property;
import com.acme.bnb.model.PropertyAttribute;
import com.acme.bnb.model.PropertyAttributeValue;
import com.acme.bnb.model.PropertyPicture;
import com.acme.bnb.model.Request;
import com.acme.bnb.model.SocialIdentity;
import com.acme.bnb.model.SysConfig;
import com.acme.bnb.model.Tenant;
import com.acme.bnb.model.datatype.CreditCard;
import com.acme.bnb.model.datatype.Phone;
import com.acme.bnb.model.enums.RequestState;
import com.acme.bnb.repositories.ActorRepository;
import com.acme.bnb.repositories.AdministratorRepository;
import com.acme.bnb.repositories.AuditAttachmentRepository;
import com.acme.bnb.repositories.AuditRepository;
import com.acme.bnb.repositories.AuditorRepository;
import com.acme.bnb.repositories.CommentRepository;
import com.acme.bnb.repositories.CommentableRepository;
import com.acme.bnb.repositories.InvoiceRepository;
import com.acme.bnb.repositories.LessorRepository;
import com.acme.bnb.repositories.PropertyAttributeRepository;
import com.acme.bnb.repositories.PropertyAttributeValueRepository;
import com.acme.bnb.repositories.PropertyPictureRepository;
import com.acme.bnb.repositories.PropertyRepository;
import com.acme.bnb.repositories.RequestRepository;
import com.acme.bnb.repositories.SocialIdentityRepository;
import com.acme.bnb.repositories.SysConfigRepository;
import com.acme.bnb.repositories.TenantRepository;
import com.acme.bnb.services.B0vEBlobService;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class AcmeBnbApplication {

    public static void main(String[] args) {
        SpringApplication.run(AcmeBnbApplication.class, args);
    }

    @Bean
    CommandLineRunner init(
            ActorRepository actorRepo,
            CommentableRepository commentableRepo,
            AdministratorRepository administratorRepo,
            LessorRepository lessorRepo,
            TenantRepository tenantRepo,
            AuditorRepository auditorRepo,
            PropertyRepository propertyRepo,
            PropertyAttributeRepository propertyAttributeRepo,
            PropertyAttributeValueRepository propertyAttributeValueRepo,
            PropertyPictureRepository propertyPictureRepo,
            RequestRepository requestRepo,
            CommentRepository commentRepo,
            AuditRepository auditRepo,
            AuditAttachmentRepository auditAttachmentRepo,
            SocialIdentityRepository socialIdentityRepo,
            InvoiceRepository invoiceRepo,
            SysConfigRepository sysConfigRepo,
            BCryptPasswordEncoder bCryptPasswordEncoder,
            B0vEBlobService blobService,
            @Value(value = "classpath:test.jpg") Resource testJpg
    ) {
        return args -> {
            //System.out.println("Blob Service Test: "+blobService.storeBlob("data:image/jpg;base64,"+Base64.getEncoder().encodeToString(IOUtils.toByteArray(testJpg.getInputStream())), true, true));

            if (sysConfigRepo.count() == 0) {
                sysConfigRepo.save(new SysConfig("feeTenant", "1.00"));
                sysConfigRepo.save(new SysConfig("feeLessor", "1.00"));
                sysConfigRepo.save(new SysConfig("bnbVat", "999999999"));
            }
            if (actorRepo.count() == 0) {
                //Atriobutos de propiedades
                PropertyAttribute countryAttr = new PropertyAttribute("Country", true);
                PropertyAttribute provinceAttr = new PropertyAttribute("Province", true);
                PropertyAttribute stateAttr = new PropertyAttribute("State", true);
                PropertyAttribute cityAttr = new PropertyAttribute("City", true);
                PropertyAttribute capacityAttr = new PropertyAttribute("Capacity", true);
                PropertyAttribute nbedsAttr = new PropertyAttribute("Number of beds", false);
                propertyAttributeRepo.save(countryAttr);
                propertyAttributeRepo.save(provinceAttr);
                propertyAttributeRepo.save(stateAttr);
                propertyAttributeRepo.save(cityAttr);
                propertyAttributeRepo.save(capacityAttr);
                propertyAttributeRepo.save(nbedsAttr);
                
                //Usuarios
                Administrator admin1 = new Administrator();
                admin1.setEmail("admin@acme.com");
                admin1.setName("Admin");
                admin1.setSurname("B0vE");
                admin1.setPhone(new Phone("192168101", "ESP"));
                admin1.setPwd(bCryptPasswordEncoder.encode("1234"));
                administratorRepo.save(admin1);

                Commentable comm1 = new Commentable();
                commentableRepo.save(comm1);
                Lessor lessor1 = new Lessor();
                lessor1.setEmail("lessor1@acme.com");
                lessor1.setName("Lessor 1");
                lessor1.setSurname("B0vE");
                lessor1.setPhone(new Phone("192168102", "ESP"));
                lessor1.setPwd(bCryptPasswordEncoder.encode("1234"));
                lessor1.setCommentable(comm1);
                lessorRepo.save(lessor1);
                
                Commentable comm2 = new Commentable();
                commentableRepo.save(comm2);
                Tenant tenant1 = new Tenant();
                tenant1.setEmail("tenant1@acme.com");
                tenant1.setName("Tenant 1");
                tenant1.setSurname("B0vE");
                tenant1.setPhone(new Phone("192168103", "ESP"));
                tenant1.setPwd(bCryptPasswordEncoder.encode("1234"));
                tenant1.setCreditCard(new CreditCard("1358954993914435", 7, 2022, "144"));
                tenant1.setSmoker(true);
                tenant1.setCommentable(comm2);
                tenantRepo.save(tenant1);
                
                Auditor auditor1 = new Auditor();
                auditor1.setEmail("auditor1@acme.com");
                auditor1.setName("Auditor 1");
                auditor1.setSurname("B0vE");
                auditor1.setPhone(new Phone("192168104", "ESP"));
                auditor1.setPwd(bCryptPasswordEncoder.encode("1234"));
                auditor1.setCompany("Intel");
                auditorRepo.save(auditor1);
                
                Commentable comm3 = new Commentable();
                commentableRepo.save(comm3);
                Tenant tenant2 = new Tenant();
                tenant2.setEmail("tenant2@acme.com");
                tenant2.setName("Tenant 2");
                tenant2.setSurname("B0vE");
                tenant2.setPhone(new Phone("192168105", "ESP"));
                tenant2.setPwd(bCryptPasswordEncoder.encode("1234"));
                tenant2.setCommentable(comm3);
                tenantRepo.save(tenant2);
                
                Commentable comm4 = new Commentable();
                commentableRepo.save(comm4);
                Lessor lessor2 = new Lessor();
                lessor2.setEmail("lessor2@acme.com");
                lessor2.setName("Lessor 2");
                lessor2.setSurname("B0vE");
                lessor2.setPhone(new Phone("192168106", "ESP"));
                lessor2.setPwd(bCryptPasswordEncoder.encode("1234"));
                lessor2.setCommentable(comm4);
                lessorRepo.save(lessor2);
                
                //Redes sociales
                socialIdentityRepo.save(new SocialIdentity("B0vE", "http://twitter.com/borjinlive", "Twitter", lessor1));
                socialIdentityRepo.save(new SocialIdentity("B0vE", "http://facebook.com/borja.lopez.pineda", "Facebook", lessor1));

                //Propiedades
                Property property1 = new Property();
                property1.setAddress("Avenida Guatemala 40");
                property1.setDescription("Dónde vivo los fines de semana. Acogedora y nada calurosa.");
                property1.setName("Casa Bore");
                property1.setPropietary(lessor1);
                property1.setRate(25D);
                property1.setNRequests(1);
                property1.setNAudits(1);
                propertyRepo.save(property1);
                property1.setDate(new GregorianCalendar(2022, 3, 25, 16, 15, 0).getTime());
                property1.setAttributes(List.of(
                        new PropertyAttributeValue("España", countryAttr, property1),
                        new PropertyAttributeValue("Huelva", provinceAttr, property1),
                        new PropertyAttributeValue("Andalucía", stateAttr, property1),
                        new PropertyAttributeValue("Huelva", cityAttr, property1),
                        new PropertyAttributeValue("4", capacityAttr, property1),
                        new PropertyAttributeValue("3", nbedsAttr, property1)
                ));
                propertyRepo.save(property1);
                property1.getAttributes().forEach(a -> propertyAttributeValueRepo.save(a));

                propertyPictureRepo.save(new PropertyPicture("https://blobcontainer.b0ve.com/deliver.php?q=0f260249e6acb86b2a495ce38bbf874a26d2c85771359078c2196d3893cf1d7a", property1));
                propertyPictureRepo.save(new PropertyPicture("https://blobcontainer.b0ve.com/deliver.php?q=54a708ac07d8662d1f16cb7daf8204d0c296777c6be2ebed0f4fb210c416a628", property1));
                propertyPictureRepo.save(new PropertyPicture("https://blobcontainer.b0ve.com/deliver.php?q=59a110c9f47dc4666363687a1c67af1365977de565748a98978e431b994c1e66", property1));
                propertyPictureRepo.save(new PropertyPicture("https://blobcontainer.b0ve.com/deliver.php?q=924611c1e49e08fb3eea9e26ccb1be38fcbcf447c4a1831a2033d0e9a652834b", property1));
                
                Property property2 = new Property();
                property2.setAddress("Calle Plus Ultra 9");
                property2.setDescription("Dónde vivo entre semana. A veces llueve dentro del piso, ya está casi arreglado.");
                property2.setName("Piso Franco");
                property2.setPropietary(lessor1);
                property2.setNRequests(1);
                property2.setAttributes(List.of(
                        new PropertyAttributeValue("España", countryAttr, property2),
                        new PropertyAttributeValue("Huelva", provinceAttr, property2),
                        new PropertyAttributeValue("Andalucía", stateAttr, property2),
                        new PropertyAttributeValue("Huelva", cityAttr, property2),
                        new PropertyAttributeValue("3", capacityAttr, property2)
                ));
                property2.setRate(20D);
                propertyRepo.save(property2);
                
                propertyPictureRepo.save(new PropertyPicture("https://blobcontainer.b0ve.com/deliver.php?q=1e46e9e0919acd10ced978494ac2d7698086de8f1870fdda90f260f6edee4fb5", property2));
                propertyPictureRepo.save(new PropertyPicture("https://blobcontainer.b0ve.com/deliver.php?q=76125ff32c958e5030c8200d8dbe001c641a28d1b6eca438014a8e5ef562b465", property2));
                
                Property property3 = new Property();
                property3.setAddress("Triana 22");
                property3.setDescription("Seamos sinceros, es un zulo indigno.");
                property3.setName("Choza paco");
                property3.setPropietary(lessor2);
                property3.setAttributes(List.of(
                        new PropertyAttributeValue("España", countryAttr, property3),
                        new PropertyAttributeValue("Sevilla", provinceAttr, property3),
                        new PropertyAttributeValue("Andalucía", stateAttr, property3),
                        new PropertyAttributeValue("Sevilla", cityAttr, property3),
                        new PropertyAttributeValue("1", capacityAttr, property3)
                ));
                property3.setRate(5.5D);
                propertyRepo.save(property3);

                //Solicitudes
                Request request1 = new Request();
                request1.setCheckIn(new GregorianCalendar(2022, 4, 25).getTime());
                request1.setCheckOut(new GregorianCalendar(2022, 4, 26).getTime());
                request1.setProperty(property1);
                request1.setSmoker(true);
                request1.setStatus(RequestState.ACCEPTED);
                request1.setTenant(tenant1);
                request1.setRate(25.2);
                request1.setTenantCreditCard(new CreditCard("11112222333344440001", 8, 2022, "144"));
                request1.setLessorCreditCard(new CreditCard("11112222333344440002", 8, 2022, "144"));
                request1.setTenantFee(1.0);
                request1.setLessorFee(1.0);
                requestRepo.save(request1);
                
                Request request2 = new Request();
                request2.setCheckIn(new GregorianCalendar(2022, 4, 25).getTime());
                request2.setCheckOut(new GregorianCalendar(2022, 4, 26).getTime());
                request2.setProperty(property2);
                request2.setSmoker(true);
                request2.setStatus(RequestState.ACCEPTED);
                request2.setTenant(tenant2);
                request2.setRate(25.2);
                request2.setTenantCreditCard(new CreditCard("11112222333344440001", 8, 2022, "144"));
                request2.setLessorCreditCard(new CreditCard("11112222333344440002", 8, 2022, "144"));
                request2.setTenantFee(1.0);
                request2.setLessorFee(1.0);
                requestRepo.save(request2);
                
                //Comentarios
                Comment comment = new Comment();
                comment.setAuthor(admin1);
                comment.setTarget(lessor1.getCommentable());
                comment.setStars(3);
                comment.setTitle("Buen tipo");
                comment.setText("Pero no es oro todo lo que reluce.");
                commentRepo.save(comment);

                //Auditorias
                Audit audit1 = new Audit();
                audit1.setDraft(false);
                audit1.setAuthor(auditor1);
                audit1.setProperty(property1);
                audit1.setText("Un sitio agradable. Los materiales eran de calidad.\nPero los vecinos son insoportables.");
                auditRepo.save(audit1);

                auditAttachmentRepo.save(new AuditAttachment("https://blobcontainer.b0ve.com/deliver.php?q=1417c794091cb6fe7c595926992fa2f195dacd7259d87b5fd2437edaf80d5a0f", audit1));
                auditAttachmentRepo.save(new AuditAttachment("https://blobcontainer.b0ve.com/deliver.php?q=0a6598eb3134067b00205d9ab94788d49cbe9e7fb73e8f8edb250590018cfa2b", audit1));

                
                System.out.println("Printing Actors: ");
                StreamSupport.stream(actorRepo.findAll().spliterator(), false).map(Actor::getName).forEach(System.out::println);
                System.out.println("-------------------------");
            }
        };
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults(""); // Remove the ROLE_ prefix
    }
}
