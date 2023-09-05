import java.util.List;
import java.util.Scanner;

public interface IProduct {
    public float MIN_INTEREST_RATE = 0.2F;
    void inputData(Scanner scanner, List<Product> listProduct);
    void displayData(List<Category> categoryList);
    void calProfit();
}
