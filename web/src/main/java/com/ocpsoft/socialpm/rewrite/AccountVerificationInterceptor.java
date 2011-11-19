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
package com.ocpsoft.socialpm.rewrite;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletContext;

import com.ocpsoft.rewrite.config.Configuration;
import com.ocpsoft.rewrite.config.ConfigurationBuilder;
import com.ocpsoft.rewrite.config.Direction;
import com.ocpsoft.rewrite.servlet.config.DispatchType;
import com.ocpsoft.rewrite.servlet.config.Forward;
import com.ocpsoft.rewrite.servlet.config.HttpConfigurationProvider;
import com.ocpsoft.rewrite.servlet.config.rule.Join;
import com.ocpsoft.socialpm.domain.user.Profile;
import com.ocpsoft.socialpm.security.Account;

/**
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 */
public class AccountVerificationInterceptor extends HttpConfigurationProvider
{
   @Inject
   private Account account;

   @PersistenceContext
   private EntityManager em;

   @Override
   public Configuration getConfiguration(final ServletContext context)
   {
      ConfigurationBuilder config = ConfigurationBuilder.begin();
      account.setEntityManager(em);
      Profile current = account.getLoggedIn();
      if (current.isPersistent() && !current.isUsernameConfirmed())
      {
         return config.defineRule()
                  .when(DispatchType.isRequest()
                           .and(Direction.isInbound())
                           .and(SocialPMResources.excluded()))
                  .perform(Forward.to("/account/confirm"))

                  .addRule(Join.path("/account/confirm").to("/pages/accountConfirm.xhtml"));
      }
      return config;
   }

   @Override
   public int priority()
   {
      return 0;
   }
}
