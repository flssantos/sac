package br.com.exception;

import javax.faces.context.ExceptionHandlerFactory;
import javax.faces.context.ExceptionHandler;

public class CustomExceptionHandlerFactory extends ExceptionHandlerFactory {
   private ExceptionHandlerFactory parent;
 
   // this injection handles jsf
   public CustomExceptionHandlerFactory(ExceptionHandlerFactory parent) {
    this.parent = parent;
   }
 
    @Override
    public ExceptionHandler getExceptionHandler() {
 
        ExceptionHandler handler = new CustomExceptionHandler(parent.getExceptionHandler());
 
        return handler;
    }
 
}
