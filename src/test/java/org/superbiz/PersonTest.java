package org.superbiz;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.ShouldMatchDataSet;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static org.junit.Assert.assertNotNull;

@RunWith(Arquillian.class)
public class PersonTest
{
    @Deployment
    public static Archive<?> createDeploymentPackage()
    {
        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addPackage(Person.class.getPackage())
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource("META-INF/persistence.xml", "persistence.xml");
    }

    @Inject
    private PersonBean bean;

    @Test
    @Transactional(TransactionMode.COMMIT)
    @UsingDataSet("datasets/person.yml")
    @ShouldMatchDataSet("datasets/expected-person.yml")
    public void personBeanTest() throws Exception
    {
        final Person p = bean.findById(1);
        assertNotNull(p);
        p.setName("foo");
        bean.newPerson(2, "dummy");
    }
}
