/*
 * Copyright 2011 <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ocpsoft.socialpm.security;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.TransactionAttribute;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.jboss.seam.security.Identity;

import com.ocpsoft.socialpm.cdi.LoggedIn;
import com.ocpsoft.socialpm.cdi.Web;
import com.ocpsoft.socialpm.domain.user.Profile;
import com.ocpsoft.socialpm.model.ProfileService;

/**
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 * 
 */
@Named("account")
@RequestScoped
public class Account implements Serializable
{
   private static final long serialVersionUID = 8474539305281711165L;

   @Inject
   @Web
   private EntityManager em;

   @Inject
   private Identity identity;

   @Inject
   private ProfileService ps;

   @PostConstruct
   public void init()
   {
      ps.setEntityManager(em);
   }

   Profile loggedIn = new Profile();

   @Produces
   @LoggedIn
   @RequestScoped
   @Named("userProfile")
   public Profile getLoggedIn()
   {
      if (identity.isLoggedIn() && !loggedIn.isPersistent())
      {
         try {
            loggedIn = ps.getProfileByIdentityKey(identity.getUser().getKey());
         }
         catch (NoResultException e) {
            throw e;
         }
      }
      else if (!identity.isLoggedIn())
      {}
      return loggedIn;
   }

   @TransactionAttribute
   public void saveAjax()
   {
      Profile current = getLoggedIn();
      ps.save(current);
   }

   @TransactionAttribute
   public void displayBootcampAjax()
   {
      Profile current = getLoggedIn();
      current.setShowBootcamp(true);
      ps.save(current);
   }

   @TransactionAttribute
   public void dismissBootcampAjax()
   {
      Profile current = getLoggedIn();
      current.setShowBootcamp(false);
      ps.save(current);
   }

   public void setEntityManager(final EntityManager em)
   {
      this.em = em;
      ps.setEntityManager(em);
   }

}
