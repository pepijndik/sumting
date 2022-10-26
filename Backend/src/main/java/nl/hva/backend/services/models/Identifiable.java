package nl.hva.backend.services.models;

public interface Identifiable<T>{
    T getId();
    void setId(T id);
}