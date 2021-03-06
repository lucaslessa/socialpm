package com.ocpsoft.socialpm.gwt.client.local.activity;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.ocpsoft.socialpm.gwt.client.local.ClientFactory;
import com.ocpsoft.socialpm.gwt.client.local.view.LoginView;

@Dependent
public class SignupActivity extends AbstractActivity implements LoginView.Presenter
{
   private final ClientFactory clientFactory;
   private final LoginView loginView;

   @Inject
   public SignupActivity(ClientFactory clientFactory, LoginView loginView)
   {
      this.loginView = loginView;
      this.clientFactory = clientFactory;
   }

   @Override
   public void start(AcceptsOneWidget containerWidget, EventBus eventBus)
   {
      loginView.setPresenter(this);

      containerWidget.setWidget(loginView.asWidget());
   }

   @Override
   public String mayStop()
   {
      return null;
   }

   @Override
   public void goTo(Place place)
   {
      clientFactory.getPlaceController().goTo(place);
   }

   @Override
   public void doLogin(String username, String password)
   {

   }

}