public enum Escalera {
     TERCIO,
     CUARTO, 
     QUINTO,
     SEXTA,
     SEPTIMA,
     OCTAVA,
     NOVENA,
     DECIMA;
     
    public String toString() {
        switch(this) {
            case TERCIO: return "Tercia";
            case CUARTO: return "Cuarta";
            case QUINTO: return "Quinta";
            case SEXTA: return "Sexta";
            case SEPTIMA: return "Septima";
            case OCTAVA: return "Octava";
            case NOVENA: return "Novena";
            case DECIMA: return "Decima";
            default: return name();
        }
    }
}
