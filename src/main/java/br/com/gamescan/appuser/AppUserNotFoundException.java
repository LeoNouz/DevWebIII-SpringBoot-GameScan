package br.com.gamescan.appuser;

public class AppUserNotFoundException extends Throwable {
    public AppUserNotFoundException(String message) {
        super(message);
    }
}