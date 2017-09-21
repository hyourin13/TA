package mochamad.ulin.nuha.ta;

/**
 * Created by Juned on 1/20/2017.
 */

public class subjects {

    String SubjectName, SubjectID, SubjectJeneng, Subjecttgl;

    public String getName() {

        return SubjectName;
    }

    public void setName(String TempName) {

        this.SubjectName = TempName;
    }


    public String getID() {
        return SubjectID;
    }

    public void setID(String subjectID) {
        SubjectID = subjectID;
    }


    public String gettgl() {
        return Subjecttgl;
    }

    public void settgl(String subjecttgl) {
        Subjecttgl = subjecttgl;
    }

    public String getJeneng() {
        return SubjectJeneng;
    }

    public void setJeneng(String subjectJeneng) {
        SubjectJeneng = subjectJeneng;
    }
}
