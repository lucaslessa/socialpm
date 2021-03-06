package com.ocpsoft.socialpm.gwt.client.local.places;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gwt.place.shared.Place;
import com.ocpsoft.socialpm.gwt.client.local.history.HistoryConstants;

public class NewStoryPlace extends Place
{
   private final String username;
   private final String slug;

   public NewStoryPlace(String username, String slug)
   {
      this.username = username;
      this.slug = slug;
   }

   public static class Tokenizer implements TypedPlaceTokenizer<NewStoryPlace>
   {
      @Override
      public String getToken(NewStoryPlace place)
      {
         return place.getUsername() + "/" + place.getSlug() + "/new-story";
      }

      @Override
      public NewStoryPlace getPlace(String token)
      {
         String[] tokens = token.split(HistoryConstants.DELIMETER(), -1);
         List<String> list = new ArrayList<String>(Arrays.asList(tokens));
         if (list.size() == 3)
         {
            return new NewStoryPlace(list.get(0), list.get(1));
         }
         return null;
      }

      @Override
      public Class<NewStoryPlace> getPlaceType()
      {
         return NewStoryPlace.class;
      }
   }

   public String getUsername()
   {
      return username;
   }

   public String getSlug()
   {
      return slug;
   }

   @Override
   public String toString()
   {
      return getClass().getName() + "[" + username + "/" + slug + "]";
   }
}