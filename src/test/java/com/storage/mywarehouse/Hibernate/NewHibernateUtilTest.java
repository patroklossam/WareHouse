package com.storage.mywarehouse.Hibernate;

import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@SuppressStaticInitializationFor("com.storage.mywarehouse.Entity.NewHibernateUtil")
public class NewHibernateUtilTest {

    NewHibernateUtil sut;

    @Mock
    SessionFactory sessionFactory;

    @Before
    public void setUp() {
        sut = new NewHibernateUtil();
    }

    @Test
    public void testSuppressStaticInitializer() {
        Assert.assertNotNull(sut);
    }
}
