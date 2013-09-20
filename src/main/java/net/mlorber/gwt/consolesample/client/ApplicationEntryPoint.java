package net.mlorber.gwt.consolesample.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import net.mlorber.gwt.console.client.Console;
import net.mlorber.gwt.console.client.notification.NotificationFactory.NotificationType;
import net.mlorber.gwt.console.client.notification.SimpleNotificationFactory;
import net.mlorber.gwt.console.client.util.ExceptionParser;
import net.mlorber.gwt.consolesample.shared.SampleException;
import net.mlorber.gwt.consolesample.shared.SampleRemoteService;
import net.mlorber.gwt.consolesample.shared.SampleRemoteServiceAsync;
import net.mlorber.gwt.consolesample.shared.SampleRuntimeException;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;

public class ApplicationEntryPoint implements EntryPoint {

   @Override
   public void onModuleLoad() {
      // JQueryNotificationFactory
      // message si missing jquery
      Console.get().init(Logger.getLogger(""), Level.WARNING, new SimpleNotificationFactory()).registerShorcut().setUnknownErrorMessage(
               "Erreur inconnue : ");

      setUncaughtExceptionHandler();

      FlowPanel rootContainer = new FlowPanel();
      RootPanel.get().add(rootContainer);

      final SampleRemoteServiceAsync service = GWT.create(SampleRemoteService.class);

      rootContainer.add(new Button("Remote SampleException") {
         {
            addClickHandler(new ClickHandler() {

               @Override
               public void onClick(ClickEvent event) {
                  service.hey(new AsyncCallback<String>() {

                     @Override
                     public void onSuccess(String result) {
                     }

                     @Override
                     public void onFailure(Throwable caught) {
                        Console.get().showNotification(caught.getMessage(), NotificationType.WARNING);
                     }
                  });
               }
            });
         }
      });
      rootContainer.add(new Button("Remote SampleRuntimeException") {
         {
            addClickHandler(new ClickHandler() {

               @Override
               public void onClick(ClickEvent event) {
                  service.boum(new AsyncCallback<String>() {

                     @Override
                     public void onSuccess(String result) {
                     }

                     @Override
                     public void onFailure(Throwable caught) {
                        Console.get().showNotification(caught.getMessage(), NotificationType.WARNING);
                     }
                  });
               }
            });
         }
      });
      rootContainer.add(new Button("Remote Runtime") {
         {
            addClickHandler(new ClickHandler() {

               @Override
               public void onClick(ClickEvent event) {
                  service.houstonWeHaveAProblem(new AsyncCallback<String>() {

                     @Override
                     public void onSuccess(String result) {
                     }

                     @Override
                     public void onFailure(Throwable caught) {
                        Console.get().showNotification(caught.getMessage(), NotificationType.WARNING);
                     }
                  });
               }
            });
         }
      });
      rootContainer.add(new Button("SampleException") {
         {
            addClickHandler(new ClickHandler() {

               @Override
               public void onClick(ClickEvent event) {
                  try {
                     throw new SampleException("coucou");
                  } catch (SampleException e) {
                     throw new RuntimeException(e);
                  }
               }
            });
         }
      });
      rootContainer.add(new Button("SampleRuntimeException") {
         {
            addClickHandler(new ClickHandler() {

               @Override
               public void onClick(ClickEvent event) {
                  try {
                     throw new SampleRuntimeException("coucou");
                  } catch (SampleRuntimeException e) {
                     throw new RuntimeException(e);
                  }
               }
            });
         }
      });
      rootContainer.add(new Button("Runtime") {
         {
            addClickHandler(new ClickHandler() {

               @Override
               public void onClick(ClickEvent event) {
                  throw new RuntimeException("What a runtime exception message");
               }
            });
         }
      });
      rootContainer.add(new Button("Juste notif") {
         {
            addClickHandler(new ClickHandler() {

               @Override
               public void onClick(ClickEvent event) {
                  Console.get().showNotification("hello i'm here", NotificationType.INFO);
               }
            });
         }
      });
   }

   private void setUncaughtExceptionHandler() {
      // ExceptionParser permet de parser les exceptions encapsulées dans une UmbrellaException
      // le nombre d'encapsulation n'est pas du tout stable, l'ExceptionParser est récursif
      // pas nécessairement la meilleure façon de gérer les exceptions qui sont gérées ici
      // (cad que le uncaught devrait rester... un uncaught => si une exception doit être
      // gérée c'est en amont) mais pour l'instant fait très bien le job
      final ExceptionParser exceptionParser = new ExceptionParser() {
         @Override
         protected boolean handleLoop(Throwable throwable) {
            return false;
         }
      };
      GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {
         @Override
         public void onUncaughtException(Throwable throwable) {
            if (!exceptionParser.parse(throwable)) {
               Console.get().showNotification(throwable.getMessage(), NotificationType.WARNING);
               // so dev logger is still used
               Console.get().logUncaughtException(throwable);
            }
         }
      });
   }

}
