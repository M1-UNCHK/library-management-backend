package sn.unchk.librarymanagement.event;

public enum EventType {
    LOAN_ADDED,
    LOAN_RETURNED,
    LOAN_DUE_DATE, LOAN_DELAY;

    public String retrieveTemplate(){
        return switch (this){
            case LOAN_ADDED -> "new-loan";
            case LOAN_RETURNED -> "loan-returned";
            case LOAN_DUE_DATE -> "loan-dueDate";
            case LOAN_DELAY -> "loan-delay";
        };
    }

    public String retrieveSubject() {
        return switch (this){
            case LOAN_ADDED -> "Notification :: Nouvel emprunt";
            case LOAN_RETURNED -> "Notification :: Livre retourné";
            case LOAN_DUE_DATE -> "Notification :: Livre à échéance";
            case LOAN_DELAY -> "Notification :: Livre à échéance";
        };
    }
}
