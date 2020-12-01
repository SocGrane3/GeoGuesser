package cat.itb.geoguesser;

import androidx.lifecycle.ViewModel;
import java.util.Random;

public class QuizViewModel extends ViewModel {
    private int index = -1, numHints;
    private int [] order = {0,1,2,3,4,5,6,7,8,9};
    private boolean hint = false;
    private double score = 0;

    public void setOrder() {
        shuffleArray(order);
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setNumHints(int numHints) {
        this.numHints = numHints;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public boolean isHint() {
        return hint;
    }

    public void setHint(boolean hint) {
        this.hint = hint;
    }

    public int getIndex() {
        return index;
    }

    public int[] getOrder() {
        return order;
    }

    public int getNumHints() {
        return numHints;
    }

    public double getScore() {
        return score;
    }

    private static void shuffleArray(int[] array) {
        int index;
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--)
        {
            index = random.nextInt(i + 1);
            if (index != i)
            {
                array[index] ^= array[i];
                array[i] ^= array[index];
                array[index] ^= array[i];
            }
        }
    }
}
