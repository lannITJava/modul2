import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class Store {
    static List<Category> listCategory = new ArrayList<>();
    static List<Product> listProduct = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);
    static boolean checkChoice = true;
    static int choice = 0;
    static boolean checkChoiceCategory = true;
    static int choiceCategory = 0;
    static boolean checkChoiceProduct = true;
    static int choiceProduct = 0;
    static int[] arrCategory;
    static int arrCategoryIndex;
    static int[] arrCountCategory;
    public static void main(String[] args) {
        do {
    //        String leftAlignFormat = "| %-15s | %-4d |%n";
            System.out.format("+------------------------------------+%n");
            System.out.printf("|");
            System.out.printf(Color.YELLOW+"       ===== QUẢN LÝ KHO =====      "+Color.ANSI_RESET);
            System.out.printf("|\n");
            System.out.format("+------------------------------------+%n");
            System.out.printf("|");
            System.out.printf("       1. Quản lý danh mục          ");
            System.out.printf("|\n");
            System.out.format("+------------------------------------+%n");
            System.out.printf("|");
            System.out.printf("       2. Quản lý sản phẩm          ");
            System.out.printf("|\n");
            System.out.format("+------------------------------------+%n");
            System.out.printf("|");
            System.out.printf("       3. Thoát                     ");
            System.out.printf("|\n");
            System.out.format("+------------------------------------+%n");
            System.out.println(Color.CYAN+"Sự lựa chọn của bạn là: "+Color.ANSI_RESET);
            Store.tryCatchChoice();
            System.out.println(Color.BLUE+"********************************************************************"+Color.ANSI_RESET);
            switch (choice) {
                case 1 -> Store.categoryMenu();
                case 2 -> Store.productMenu();
                case 3 -> System.exit(0);
                default -> System.err.println("Vui lòng từ 1-3");
            }
        }while (true);
    }
    public static void tryCatchChoice(){
        do {
            try{
                choice = Integer.parseInt(scanner.nextLine());
                checkChoice = false;
            }catch (NumberFormatException ex1){
                System.err.println("Sự lựa chọn phải là số nguyên, vui lòng nhập lại");
            }catch (Exception ex){
                System.err.println("Đã xảy ra lỗi trong quá trình xử lý");
            }
        }while (checkChoice);
    }
    public static void categoryMenu(){
        readCategory();
        boolean isExit = true;
        do {
            System.out.format("+------------------------------------------------------------------+%n");
            System.out.printf("|");
            System.out.printf(Color.GREEN+"                    ===== QUẢN LÝ DANH MỤC =====                  "+Color.ANSI_RESET);
            System.out.printf("|\n");
            System.out.format("+------------------------------------------------------------------+%n");
            System.out.printf("|");
            System.out.printf("          1. Thêm mới danh mục                                    ");
            System.out.printf("|\n");
            System.out.format("+------------------------------------------------------------------+%n");
            System.out.printf("|");
            System.out.printf("          2. Cập nhật danh mục                                    ");
            System.out.printf("|\n");
            System.out.format("+------------------------------------------------------------------+%n");
            System.out.printf("|");
            System.out.printf("          3. Xóa danh mục                                         ");
            System.out.printf("|\n");
            System.out.format("+------------------------------------------------------------------+%n");
            System.out.printf("|");
            System.out.printf("          4. Tìm kiếm danh mục theo tên danh mục                  ");
            System.out.printf("|\n");
            System.out.format("+------------------------------------------------------------------+%n");
            System.out.printf("|");
            System.out.printf("          5. Thống kê số lượng sản phẩm đang có trong danh mục    ");
            System.out.printf("|\n");
            System.out.format("+------------------------------------------------------------------+%n");
            System.out.printf("|");
            System.out.printf("          6. Quay lại                                             ");
            System.out.printf("|\n");
            System.out.format("+------------------------------------------------------------------+%n");

            System.out.println(Color.CYAN+"Sự lựa chọn của bạn là: "+Color.ANSI_RESET);
            Store.tryCatchCategoryChoice();
            System.out.println(Color.BLUE+"********************************************************************"+Color.ANSI_RESET);
            switch (choiceCategory){
                case 1:
                    //Thêm mới danh mục
                    Store.inputCategory();
                    writeCategoryToFile(listCategory);
                    break;
                case 2:
                    //Cập nhật danh mục
                    Store.updateCategory();
                    writeCategoryToFile(listCategory);
                    break;
                case 3:
                    //Xóa danh mục
                    Store.deleteCategory();
                    writeCategoryToFile(listCategory);
                    break;
                case 4:
                    //Tìm kiếm danh mục theo tên danh mục
                    Store.searchCategoryByName();
                    break;
                case 5:
                    //Thống kê số lượng sp đang có trong danh mục
                    Store.getNumberProductByCategory();
                    break;
                case 6:
                    isExit= false;
                    break;
                default:
                    System.err.println("Vui lòng từ 1-6");
            }
        }while (isExit);
    }
    public static void tryCatchCategoryChoice(){
        do {
            try{
                choiceCategory = Integer.parseInt(scanner.nextLine());
                checkChoiceCategory = false;
            }catch (NumberFormatException ex1){
                System.err.println("Sự lựa chọn phải là số nguyên, vui lòng nhập lại");
            }catch (Exception ex){
                System.err.println("Đã xảy ra lỗi trong quá trình xử lý");
            }
        }while (checkChoiceCategory);
    }
    //Thêm mới danh mục
    public static void inputCategory(){
        Category categoryNew = new Category();
        categoryNew.inputData(scanner,listCategory);
        listCategory.add(categoryNew);
        System.out.println(Color.YELLOW_BOLD+"Đã thêm danh mục mới thành công"+Color.ANSI_RESET);
    }
    //Cập nhật danh mục
    public static void updateCategory(){
        if (listCategory.size()==0){
            System.err.println("Không có danh mục nào");
            return;
        }
        System.out.println("Nhập mã danh mục muốn cập nhật: ");
        boolean checkUpdateCategoryId = true;
        int updateCategoryId ;
        do {
            try {
                updateCategoryId = Integer.parseInt(scanner.nextLine());
                int indexUpdateCategory = getIndexCategoryById(updateCategoryId);

                    if (indexUpdateCategory>=0){
                        // Cập nhật danh mục
                        System.out.println("Nhập vào tên danh mục cập nhật");
                        boolean checkNameUpdate = true;
                        do {
                            String updateName = scanner.nextLine();
                            boolean nameUpdateExit = false;
                            for (Category ca: listCategory) {
                                if (ca.getName().equals(updateName)){
                                    nameUpdateExit= true;
                                    break;
                                }
                            }
                            if (nameUpdateExit){
                                System.err.println("Tên danh mục đã tồn tại, vui lòng nhập lại");
                            }else {
                                if (updateName.length()>=6&& updateName.length()<=30){
                                    listCategory.get(indexUpdateCategory).setName(updateName);
                                    checkNameUpdate = false;
                                }else {
                                    System.err.println("Tên danh mục phải từ 6-30 ký tự, vui lòng nhập lại");
                                }
                            }
                        }while (checkNameUpdate);
                        System.out.println("Nhập mô tả danh mục muốn cập nhật: ");
                        boolean checkUpdateDescription = true;
                        String updateDescription;
                        do {
                            updateDescription = scanner.nextLine();
                            if (updateDescription.trim().equals("")){
                                System.err.println("Mô tả danh mục không được để trống");
                            }else {
                                listCategory.get(indexUpdateCategory).setDescription(updateDescription);
                                checkUpdateDescription= false;
                            }
                        }while (checkUpdateDescription);
                        System.out.println("Nhập trạng thái danh mục muốn cập nhật: ");
                        boolean checkUpdateStatus= true;
                        boolean updateStatus;
                        do {
                            String updateStr = scanner.nextLine();
                            if (updateStr.equalsIgnoreCase("true") || updateStr.equalsIgnoreCase("false")){
                                updateStatus = Boolean.parseBoolean(updateStr);
                                listCategory.get(indexUpdateCategory).setStatus(updateStatus);
                                checkUpdateStatus= false;
                            }else {
                                System.err.println("Dữ liệu không phải kiểu Boolean, vui lòng nhập lại");
                            }
                        }while (checkUpdateStatus);
                        System.out.println(Color.YELLOW_BOLD+"Đã cập nhật danh mục thành công"+Color.ANSI_RESET);
                        checkUpdateCategoryId = false;
                    }else {
                        System.err.println("Không tồn tại mã danh mục như vậy");
                    }
            }catch (NumberFormatException ex){
                System.err.println("Mã danh mục phải là số nguyên, vui lòng nhập lại");
            }catch (Exception ex1){
                System.err.println("Đã xảy ra lỗi trong quá trình xử lý, vui lòng nhập lại");
            }
        }while (checkUpdateCategoryId );
    }
    //Xóa danh mục
    public static void deleteCategory(){
        if (listCategory.size()==0){
            System.err.println("Không có danh mục nào");
            return;
        }
        System.out.println("Nhập vào mã danh mục cần xóa: ");
        boolean checkDeleteCategoryId = true;
        int deleteCategoryId ;
        do {
            try {
                deleteCategoryId = Integer.parseInt(scanner.nextLine());
                int indexDeleteCategory = getIndexCategoryById(deleteCategoryId);
                    if (indexDeleteCategory>=0){
                        boolean isExit = false;
                        for (Product pro: listProduct) {
                            if (pro.getCategoryId()==deleteCategoryId){
                                isExit = true;
                                break;
                            }
                        }
                        if (!isExit){
                            listCategory.remove(indexDeleteCategory);
                            System.out.println(Color.YELLOW_BOLD+"Đã xóa danh mục thành công"+Color.ANSI_RESET);
                            checkDeleteCategoryId = false;
                        }else {
                            System.err.println("Danh mục đang có sản phẩm tham chiếu, không thể xóa");
                            break;
                        }
                    }else {
                        System.err.println("Mã danh mục không tồn tại");
                    }
            }catch (NumberFormatException ex){
                System.err.println("Mã danh mục phải là số nguyên, vui lòng nhập lại");
            }catch (Exception ex1){
                System.err.println("Đã xảy ra lỗi trong quá trình xử lý, vui lòng nhập lại");
            }
        }while (checkDeleteCategoryId );

    }
    //Tìm kiếm danh mục theo tên danh mục
    public static void searchCategoryByName(){
        if (listCategory.size()==0){
            System.err.println("Không có danh mục nào");
            return;
        }
        System.out.println("Nhập tên danh mục muốn tìm kiếm: ");
        String name = scanner.nextLine();
        System.out.println(Color.YELLOW_BOLD+"Thông tin các danh mục tìm kiếm: "+Color.ANSI_RESET);
        AtomicBoolean isExit = new AtomicBoolean(false);
        listCategory.stream().filter(category -> category.getName().toLowerCase().contains(name.toLowerCase())).forEach(category -> {
            category.displayData();
            isExit.set(true);
        });
        if (!isExit.get()){
            System.err.println("Không tìm thấy danh mục");
        }
    }
    //Thống kê số lượng sp đang có trong danh mục
    public static void getNumberProductByCategory(){
        if (listCategory.size()==0){
            System.err.println("Không có sản phẩm nào");
            return;
        }
        arrCategory = new int[listCategory.size()];
        arrCategoryIndex =0;
        Store.getNumberInCategory();
        // Thống kê tần suất xuất hiện
        arrCountCategory = new int[arrCategoryIndex];
        Store.getNumberProduct();
        for (int i = 0; i < arrCategoryIndex; i++) {
            System.out.println("Danh mục "+arrCategory[i]+" có "+arrCountCategory[i]+" sản phẩm");
        }
    }
    public static void getNumberInCategory(){
        for (int i = 0; i < listCategory.size(); i++) {
            boolean check = false;
            for (int j = i+1; j <listCategory.size() ; j++) {
                if ((listCategory.get(j).getId())==listCategory.get(i).getId()) {
                    check= true;
                    break;
                }
            }
            if (!check){
                arrCategory[arrCategoryIndex]= listCategory.get(i).getId();
                arrCategoryIndex++;
            }
        }
    }
    public static void getNumberProduct(){
        for (int i = 0; i < arrCategoryIndex; i++) {
            int count = 0;
            for (Product product : listProduct) {
                if (arrCategory[i] == product.getCategoryId()) {
                    count++;
                }
            }
            arrCountCategory[i]= count;
        }
    }
    // Ghi ra categories.txt
    public static void writeCategoryToFile(List<Category> listCategory){
        File file = new File("categories.txt");
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try{
            fos = new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(listCategory);
            oos.flush();
        }catch (FileNotFoundException ex1){
            System.err.println("File không tồn tại");
        }catch (IOException ex2){
            System.err.println("Lỗi khi ghi dữ liệu ra file");
        }catch (Exception ex){
            System.err.println("Xảy ra lỗi trong quá trình ghi dữ liệu ra file");
        }finally {
            try {
                if (oos != null) {
                    oos.close();
                }
                if (fos != null) {
                    fos.close();
                }
            }catch (IOException ex){
                System.err.println("Xảy ra lỗi khi đóng các stream");
            } catch (Exception ex) {
                System.err.println("Xảy ra lỗi trong quá trình đóng các stream");
            }
        }
    }
    public static void readCategory() {
        File file = new File("categories.txt");
        if (!file.exists()){
            return;
        }
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        List<Category> listCategoryRead = null;
        try {
            fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);
            listCategoryRead = (List<Category>) ois.readObject();
        } catch (FileNotFoundException ex) {
            System.err.println("Không tồn tại file");
        } catch (IOException ex1) {
            System.err.println("Lỗi khi đọc file");
        } catch (Exception ex2) {
            System.err.println("Có lỗi trong quá trình đọc dữ liệu từ file");
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (ois != null) {
                    ois.close();
                }
            } catch (IOException ex) {
                System.err.println("Có lỗi khi đóng stream");
            } catch (Exception e1) {
                System.err.println("Có lỗi trong quá trình đóng các stream");
            }
            return;
        }
    }
    public static int getIndexCategoryById(int categoryId){
        for (int i = 0; i < listCategory.size(); i++) {
            if (listCategory.get(i).getId()==categoryId){
                return i;
            }
        }
        return -1;
    }
    public static void productMenu(){
        readProduct();
       boolean isExit = true;
        do {
            System.out.format("+------------------------------------------------------------------+%n");
            System.out.printf("|");
            System.out.printf(Color.PURPLE+"                    ===== QUẢN LÝ SẢN PHẨM =====                  "+Color.ANSI_RESET);
            System.out.printf("|\n");
            System.out.format("+------------------------------------------------------------------+%n");
            System.out.printf("|");
            System.out.printf("          1. Thêm mới sản phẩm                                    ");
            System.out.printf("|\n");
            System.out.format("+------------------------------------------------------------------+%n");
            System.out.printf("|");
            System.out.printf("          2. Cập nhật sản phẩm                                    ");
            System.out.printf("|\n");
            System.out.format("+------------------------------------------------------------------+%n");
            System.out.printf("|");
            System.out.printf("          3. Xóa sản phẩm                                         ");
            System.out.printf("|\n");
            System.out.format("+------------------------------------------------------------------+%n");
            System.out.printf("|");
            System.out.printf("          4. Hiển thị sản phẩm theo tên A-Z                       ");
            System.out.printf("|\n");
            System.out.format("+------------------------------------------------------------------+%n");
            System.out.printf("|");
            System.out.printf("          5. Hiển thị sản phẩm theo lợi nhuận từ cao-thấp         ");
            System.out.printf("|\n");
            System.out.format("+------------------------------------------------------------------+%n");
            System.out.printf("|");
            System.out.printf("          6. Tìm kiếm sản phẩm                                    ");
            System.out.printf("|\n");
            System.out.format("+------------------------------------------------------------------+%n");
            System.out.printf("|");
            System.out.printf("          7. Quay lại                                             ");
            System.out.printf("|\n");
            System.out.format("+------------------------------------------------------------------+%n");
            System.out.println(Color.CYAN+"Sự lựa chọn của bạn là: "+Color.ANSI_RESET);
            Store.tryCatchProductChoice();
            System.out.println(Color.BLUE+"********************************************************************"+Color.ANSI_RESET);
            switch (choiceProduct){
                case 1:
                    // Thêm mới sản phẩm
                    Store.inputProduct();
                    writeProductToFile(listProduct);
                    break;
                case 2:
                    //Cập nhật sản phẩm
                    Store.updateProduct();
                    writeProductToFile(listProduct);
                    break;
                case 3:
                    //Xóa sản phẩm
                    Store.deleteProduct();
                    writeProductToFile(listProduct);
                    break;
                case 4:
                    //Hiển thị sản phẩm theo tên A-Z
                    Store.displayProductByName();
                    break;
                case 5:
                    //Hiển thị sản phẩm theo lợi nhuận từ cao-thấp
                    Store.displayProductByProfit();
                    break;
                case 6:
                    //Tìm kiếm sản phẩm
                    Store.searchProduct();
                    break;
                case 7:
                    isExit= false;
                    break;
                default:
                    System.err.println("Vui lòng từ 1-7");
            }
        }while (isExit);
    }
    public static void tryCatchProductChoice(){
        do {
            try{
                choiceProduct = Integer.parseInt(scanner.nextLine());
                checkChoiceProduct= false;
            }catch (NumberFormatException ex1){
                System.err.println("Sự lựa chọn phải là số nguyên, vui lòng nhập lại");
            }catch (Exception ex){
                System.err.println("Đã xảy ra lỗi trong quá trình xử lý");
            }
        }while (checkChoiceProduct);
    }
    //Thêm mới sản phẩm
    public static void inputProduct(){
        if (listCategory.size()==0){
            System.err.println("Không có danh mục nào cho sản phẩm thuộc về");
            return;
        }
        Product productNew = new Product();
        productNew.inputData(scanner, listProduct);
        System.out.println(Color.YELLOW_BOLD+"********CATALOG***********"+Color.ANSI_RESET);
        for (int i = 0; i < listCategory.size(); i++) {
            System.out.printf("%d. %s\n",i+1,listCategory.get(i).getName());
        }
        System.out.println("Chọn danh mục: ");
        boolean isExit = true;
        int choiceCategory = 0;
        do {
            try {
                choiceCategory = Integer.parseInt(scanner.nextLine());
                isExit = false;
            }catch (NumberFormatException ex){
                System.err.println("Sự lựa chọn phải là số nguyên, vui lòng nhập lại");
            }
        }while (isExit);
        int categoryIdChoice = listCategory.get(choiceCategory-1).getId();
        productNew.setCategoryId(categoryIdChoice);
        listProduct.add(productNew);
        System.out.println(Color.YELLOW_BOLD+"Đã thêm sản phẩm thành công"+Color.ANSI_RESET);
    }
    //Cập nhật sản phẩm
    public static void updateProduct(){
        if (listProduct.size()==0){
            System.err.println("Không có sản phẩm nào");
            return;
        }
        System.out.println("Nhập mã danh mục muốn cập nhật: ");
        boolean checkUpdateProductId = true;
        String updateProductId ;
        do {
            updateProductId = scanner.nextLine();
            int indexUpdateProduct = getIndexProductById(updateProductId);
            if (indexUpdateProduct>=0){
                // Tiến hành cập nhật
                System.out.println("Nhập vào tên sản phẩm cập nhật: ");
                boolean checkUpdateProName = true;
                do {
                    String updateProName = scanner.nextLine();
                    boolean isExit = false;
                    for (Product pro: listProduct) {
                        if (pro.getProductName().equals(updateProName)){
                            isExit= true;
                            break;
                        }
                    }
                    if (isExit){
                        System.err.println("Tên sản phẩm đã tồn tại, vui lòng nhập lại");
                    }else {
                        if (updateProName.length()>=6 && updateProName.length()<=30){
                            listProduct.get(indexUpdateProduct).setProductName(updateProName);
                            checkUpdateProName = false;
                        }else {
                            System.err.println("Tên sản phẩm phải gồm 6-30 ký tự, vui lòng nhập lại");
                        }
                    }
                }while (checkUpdateProName);
                System.out.println("Nhập giá nhập của sản phẩm muốn cập nhật: ");
                boolean checkUpdateImportPrice = true;
                double updateImportPrice = 0;
                do {
                    try{
                        updateImportPrice = Double.parseDouble(scanner.nextLine());
                        if (updateImportPrice>0){
                            listProduct.get(indexUpdateProduct).setImportPrice(updateImportPrice);
                            checkUpdateImportPrice = false;
                        }else {
                            System.err.println("Giá nhập sản phẩm phải lớn hơn 0, vui lòng nhập lại");
                        }
                    }catch (NumberFormatException ex){
                        System.err.println("Dữ liệu không phải là số, vui lòng nhập lại");
                    }catch (Exception ex1){
                        System.err.println("Đã xảy ra lỗi trong quá trình xử lý, vui lòng nhập lại");
                    }
                }while (checkUpdateImportPrice);
                System.out.println("Nhập giá xuất của sản phẩm muốn cập nhật: ");
                boolean checkUpdateExportPrice = true;
                do {
                    try{
                        double updateExportPrice = Double.parseDouble(scanner.nextLine());
                        if (updateExportPrice>0){
                            if (updateExportPrice>=(updateImportPrice* IProduct.MIN_INTEREST_RATE)+updateImportPrice){
                                checkUpdateExportPrice = false;
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
                }while (checkUpdateExportPrice);
                System.out.println("Nhập mô tả sản phẩm muốn cập nhật: ");
                boolean checkUpdateDes = true;
                do {
                    String updateDes = scanner.nextLine();
                    if (updateDes.trim().equals("")){
                        System.err.println("Mô tả sản phẩm không được để trống");
                    }else {
                        listProduct.get(indexUpdateProduct).setProductDescription(updateDes);
                        checkUpdateDes= false;
                    }
                }while (checkUpdateDes);
                boolean updateStatus = Product.inputBoolean(scanner);
                listProduct.get(indexUpdateProduct).setProductStatus(updateStatus);
                System.out.println(Color.YELLOW_BOLD+"Đã cập nhật sản phẩm thành công"+Color.ANSI_RESET);
                checkUpdateProductId = false;
            }else {
                System.err.println("Mã sản phẩm không tồn tại");
            }
        }while (checkUpdateProductId);
    }
    //Xóa sản phẩm
    public static void deleteProduct(){
        if (listProduct.size()==0){
            System.err.println("Không có sản phẩm nào");
            return;
        }
        System.out.println("Nhập mã sản phẩm muốn xóa: ");
        boolean checkDeleteProductId = true;
        String deleteProductId ;
        do {
            deleteProductId = scanner.nextLine();
            int indexDeleteProduct = getIndexProductById(deleteProductId);
            if (indexDeleteProduct>=0){
                listProduct.remove(indexDeleteProduct);
                System.out.println(Color.YELLOW_BOLD+"Đã xóa danh mục thành công"+Color.ANSI_RESET);
                checkDeleteProductId = false;
            }else {
                System.err.println("Mã sản phẩm không tồn tại, vui lòng nhập lại");
            }
        }while (checkDeleteProductId);
    }
    //Hiển thị sản phẩm theo tên A-Z
    public static void displayProductByName(){
        if (listProduct.size()==0){
            System.err.println("Không có sản phẩm nào");
            return;
        }
        System.out.println(Color.YELLOW_BOLD+"Thông tin sản phẩm theo tên A-Z"+Color.ANSI_RESET);
        listProduct.stream().sorted(Comparator.comparing(Product::getProductName)).forEach(product -> product.displayData(listCategory));
    }
    //Hiển thị sản phẩm theo lợi nhuận từ cao-thấp
    public static void displayProductByProfit(){
        if (listProduct.size()==0){
            System.err.println("Không có sản phẩm nào");
            return;
        }
        System.out.println(Color.YELLOW_BOLD+"Danh sách sản phẩm giảm dần theo lợi nhuận"+Color.ANSI_RESET);
        listProduct.forEach(Product::calProfit);
        listProduct.stream().sorted(Comparator.comparing(Product::getProfit).reversed()).forEach(product -> product.displayData(listCategory));
    }
    //Tìm kiếm sản phẩm
    public static void searchProduct(){
        if (listProduct.size()==0){
            System.err.println("Không có sản phẩm nào");
            return;
        }
        System.out.println("Nhập vào dữ liệu cần tìm kiếm: ");
        String searchData = scanner.nextLine();
        System.out.println(Color.YELLOW_BOLD+"THÔNG TIN SẢN PHẨM: "+Color.ANSI_RESET);
        AtomicBoolean isExit  = new AtomicBoolean(false);
        listProduct.stream().filter(product ->
                product.getProductName().toLowerCase().contains(searchData.toLowerCase()) ||
                        String.valueOf(product.getImportPrice()).contains(searchData) ||
                        String.valueOf(product.getExportPrice()).contains(searchData)
                ).forEach(product -> {
            product.displayData(listCategory);
            isExit.set(true);
        });
        if (!isExit.get()) {
            System.err.println("Không tìm thấy sản phẩm nào");
        }
    }
    public static int getIndexProductById(String productId){
        for (int i = 0; i < listProduct.size(); i++) {
            if (listProduct.get(i).getProductId().equals(productId)){
                return i;
            }
        }
        return -1;
    }
    public static void writeProductToFile(List<Product> listProduct){
        File file = new File("products.txt");
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try{
            fos = new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(listProduct);
            oos.flush();
        }catch (FileNotFoundException ex1){
            System.err.println("File không tồn tại");
        }catch (IOException ex2){
            System.err.println("Lỗi khi ghi dữ liệu ra file");
        }catch (Exception ex){
            System.err.println("Xảy ra lỗi trong quá trình ghi dữ liệu ra file");
        }finally {
            try {
                if (oos != null) {
                    oos.close();
                }
                if (fos != null) {
                    fos.close();
                }
            }catch (IOException ex){
                System.err.println("Xảy ra lỗi khi đóng các stream");
            } catch (Exception ex) {
                System.err.println("Xảy ra lỗi trong quá trình đóng các stream");
            }
        }
    }
    public static void readProduct() {
        File file = new File("products.txt");
        if (!file.exists()){
            return;
        }
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        List<Product> listProductRead = null;
        try {
            fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);
            listProductRead = (List<Product>) ois.readObject();
        } catch (FileNotFoundException ex) {
            System.err.println("Không tồn tại file");
        } catch (IOException ex1) {
            System.err.println("Lỗi khi đọc file");
        } catch (Exception ex2) {
            System.err.println("Có lỗi trong quá trình đọc dữ liệu từ file");
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (ois != null) {
                    ois.close();
                }
            } catch (IOException ex) {
                System.err.println("Có lỗi khi đóng stream");
            } catch (Exception e1) {
                System.err.println("Có lỗi trong quá trình đóng các stream");
            }
            return;
        }
    }

}
