package io.hyperfoil.core.steps;

import java.util.Collections;
import java.util.List;

import io.hyperfoil.api.session.Session;
import io.hyperfoil.api.config.Step;
import io.hyperfoil.core.builders.BaseSequenceBuilder;
import io.hyperfoil.core.builders.BaseStepBuilder;

public class AwaitDelayStep implements Step {
   private final Object key;

   public AwaitDelayStep(Object key) {
      this.key = key;
   }

   @Override
   public boolean invoke(Session session) {
      ScheduleDelayStep.Timestamp blockedUntil = (ScheduleDelayStep.Timestamp) session.getObject(key);
      return System.currentTimeMillis() >= blockedUntil.timestamp;
   }

   public static class Builder extends BaseStepBuilder {
      private Object key;

      public Builder(BaseSequenceBuilder parent) {
         super(parent);
      }

      public Builder key(String key) {
         this.key = key;
         return this;
      }

      @Override
      public List<Step> build() {
         return Collections.singletonList(new AwaitDelayStep(key));
      }
   }
}