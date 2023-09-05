import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Product implements IProduct, Serializable {
    private String productId;
    private String productName;
    private double importPrice;
    private double exportPrice;
    private double profit;
    private String productDescription;
    private boolean productStatus;
    private int categoryId;

    public Product() {
    }

    public Product(String productId, String productName, double importPrice, double exportPrice, double profit, String productDescription, int categoryId) {
        this.productId = productId;
        this.productName = productName;
        this.importPrice = importPrice;
        this.exportPrice = exportPrice;
        this.profit = profit;
        this.productDescription = productDescription;
        this.categoryId = categoryId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getImportPrice() {
        return importPrice;
    }

    public void setImportPrice(double importPrice) {
        this.importPrice = importPrice;
    }

    public double getExportPrice() {
        return exportPrice;
    }

    public void setExportPrice(double exportPrice) {
        this.exportPrice = exportPrice;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public boolean isProductStatus() {
        return productStatus;
    }

    public void setProductStatus(boolean productStatus) {
        this.productStatus = productStatus;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public void inputData(Scanner scanner, List<Product> listProduct) {
        // Validate productId
        System.out.println("Nhập vào mã sản phẩm: ");
        boolean checkId = true ;
        do {
            this.productId = scanner.nextLine();
            boolean isExit = false;
            for (Product pro: listProduct) {
                if (pro.productId.equals(this.productId)){
                    isExit = true;
                    break;
                }
            }
            if (isExit){
                System.err.println("Mã sản phẩm đã tồn tại, vui lòng nhập lại");
            }else {
                if (this.productId.charAt(0)=='P'){
                    if (this.productId.length()==4){
                        checkId= false;
                    }else {
                        System.err.println("Mã sản phẩm phải gồm 4 ký tự");
                    }
                }else {
                    System.err.println("Mã sản phẩm phải bắt đầu bằng 'P'");
                }
            }
        }while (checkId);
        // validate name
        System.out.println("Nhập vào tên sản phẩm: ");
        boolean checkName = true;
        do {
            this.productName= scanner.nextLine();
            boolean isExit = false;
            for (Product pro: listProduct) {
                if (pro.productName.equals(this.productName)){
                    isExit = true;
                    break;
                }
            }
            if (isExit){
                System.err.println("Tên sản phẩm đã tồn tại, vui lòng nhập lại");
            }else {
                if (this.productName.length()>= 6 && this.productName.length()<=30){
                    checkName = false;
                }else {
                    System.err.println("Tên sản phẩm phải gồm 6-30 ký tự, vui lòng nhập lại");
                }
            }
        }while (checkName);
        // Validate giá nhập
        System.out.println("Nhập vào giá nhập của sản phẩm: ");
        boolean checkImportPrice = true;
        do {
            try{
                this.importPrice = Double.parseDouble(scanner.nextLine());
                if (this.importPrice>0){
                    checkImportPrice = false;
                }else {
                    System.err.println("Giá nhập sản phẩm phải lớn hơn 0, vui lòng nhập lại");
                }
            }catch (NumberFormatException ex){
                System.err.println("Dữ liệu không phải là số, vui lòng nhập lại");
            }catch (Exception ex1){
                System.err.println("Đã xảy ra lỗi trong quá trình xử lý, vui lòng nhập lại");
            }
        }while (checkImportPrice);
        // Validate giá xuất
        System.out.println("Nhập vào giá xuất của sản phẩm: ");
        boolean checkExportPrice = true;
        do {
            try{
                this.exportPrice = Double.parseDouble(scanner.nextLine());
                if (this.exportPrice>0){
                    if (this.exportPrice>=(this.importPrice*MIN_INTEREST_RATE)+this.importPrice){
                        checkExportPrice = false;
                    }else {
                        System.err.println("Giá xuất sản phẩm phải có giá trị lớn hơn giá nhập ít nhất MIN_INTEREST_RATE lần, vui lòng nhập lại");
                    }
                }else {
                    System.err.println("Giá xuất sản phẩm phải lớn hơn 0, vui lòng nhập lại");
                }
            }catch (NumberFormatException ex){
                System.err.println("Dữ liệu không phải là số, vui lòng nhập lại");
            }catch (Exception ex1){
                System.err.println("Đã xảy ra lỗi trong quá trình xử lý, vui lòng nhập lại");
            }
        }while (checkExportPrice);
        // Validate mổ tả sản phẩm
        System.out.println("Nhập vào mô tả sản phẩm: ");
        boolean checkProDescription = true;
        do {
            this.productDescription = scanner.nextLine();
            if (this.productDescription.trim().equals("")){
                System.err.println("Mô tả sản phẩm không được để trống");
            }else {
                checkProDescription= false;
            }
        }while (checkProDescription);
        // Validate status
        this.productStatus = Product.inputBoolean(scanner);
    }
    public static boolean inputBoolean(Scanner scanner){
        System.out.println("Nhập vào trạng thái danh mục");
        do {
            String str = scanner.nextLine();
            if (str.equalsIgnoreCase("true") || str.equalsIgnoreCase("false")){
                return Boolean.parseBoolean(str);
            }else {
                System.err.println("Dữ liệu không phải kiểu Boolean, vui lòng nhập lại");
            }
        }while (true);
    }
    @Override
    public void displayData(List<Category> categoryList) {
        String proStatusDisplay  = this.productStatus? "Còn hàng" : "Ngừng kinh doanh";
        String categoryName = categoryList.stream().filter(category -> category.getId()==this.categoryId).toList().get(0).getName();
        System.out.format("+------------------------------------------------------------------------------------------------------------------------------------------------------+%n");
        System.out.printf("|");
        System.out.printf("  Mã sản phẩm  ");
        System.out.printf("|");
        System.out.printf("  Tên sản phẩm    ");
        System.out.printf("|");
        System.out.printf("  Giá nhập   ");
        System.out.printf("|");
        System.out.printf("  Giá xuất   ");
        System.out.printf("|");
        System.out.printf("  Lợi nhuận sản phẩm  ");
        System.out.printf("|");
        System.out.printf("  Mô tả sản phẩm        ");
        System.out.printf("|");
        System.out.printf("  Trạng thái          ");
        System.out.printf("|");
        System.out.printf("  Tên danh mục  ");
        System.out.printf("|\n");
        System.out.format("+------------------------------------------------------------------------------------------------------------------------------------------------------+%n");
        System.out.printf("|");
        System.out.printf("  %s         ",this.productId);
        System.out.printf("|");
        System.out.printf("  %s     ",this.productName);
        System.out.printf("|");
        System.out.printf("  %1f  ",this.importPrice);
        System.out.printf("|");
        System.out.printf("  %1f  ",this.exportPrice);
        System.out.printf("|");
        System.out.printf("  %1f           ",this.profit);
        System.out.printf("|");
        System.out.printf("  %s        ",this.productDescription);
        System.out.printf("|");
        System.out.printf("  %s    ",proStatusDisplay);
        System.out.printf("|");
        System.out.printf("  %s      ",categoryName);
        System.out.printf("|\n");
        System.out.format("+------------------------------------------------------------------------------------------------------------------------------------------------------+%n");
        System.out.println(Color.BLUE+"********************************************************************"+Color.ANSI_RESET);
    }

    @Override
    public void calProfit() {
        this.profit= this.exportPrice-this.importPrice;
    }
}
