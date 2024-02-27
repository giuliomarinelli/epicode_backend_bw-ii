package it.epicode.backend.bwii.epic_energy_services.Exceptions;

public class NotFoundException extends RuntimeException{
    public NotFoundException(String message){
        super(message);
    }
}
