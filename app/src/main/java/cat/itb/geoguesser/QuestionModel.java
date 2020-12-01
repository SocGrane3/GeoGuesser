package cat.itb.geoguesser;

public class QuestionModel {

    int idQuestion, idRespota1, idRespota2, idRespota3, idRespota4, idImage, respota;

    public QuestionModel(int idQuestion, int idRespota1, int idRespota2, int idRespota3, int idRespota4, int idimage, int respota) {
        this.idQuestion = idQuestion;
        this.idRespota1 = idRespota1;
        this.idRespota2 = idRespota2;
        this.idRespota3 = idRespota3;
        this.idRespota4 = idRespota4;
        this.idImage = idimage;
        this.respota = respota;
    }

    public int getRespota() {
        return respota;
    }
}
