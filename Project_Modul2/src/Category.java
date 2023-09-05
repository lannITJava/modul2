import java.io.Serializable;
import java.util.List;
import java.util.Scanner;

public class Category implements ICategory, Serializable {
    private int id ;
    private String name;
    private String description;
    private boolean status;

    public Category() {
    }

    public Category(int id, String name, String description, boolean status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
    }
//em save roi chay lai nhe
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public void inputData(Scanner scanner, List<Category> listCategory) {
        // Validate mã danh mục
        System.out.println("Nhập vào mã danh mục: ");
        boolean checkId = true;
        do {
            try {
                this.id= Integer.parseInt(scanner.nextLine());
                boolean isExit = false;
                for (Category ca: listCategory) {
                    if (ca.getId()==this.id){
                        isExit= true;
                        break;
                    }
                }
                if (isExit){
                    System.err.println("Mã danh mục đã tồn tại, vui lòng nhập lại");
                }else {
                    if (this.id>0){
                        checkId= false;
                    }else {
                        System.err.println("Mã danh mục phải lớn hơn 0");
                    }
                }
            }catch (NumberFormatException ex){
                System.err.println("Mã danh mục phải là số nguyên, vui lòng nhập lại");
            }catch (Exception ex1){
                System.err.println("Đã xảy ra lỗi trong quá trình xử lý, vui lòng nhập lại");
            }
        }while (checkId);
        // Validate tên danh mục
        System.out.println("Nhập vào tên danh mục: ");
        boolean checkName = true;
        do {
            this.name = scanner.nextLine();
            boolean nameExit = false;
            for (Category ca: listCategory) {
                if (ca.getName().equals(this.name)){
                    nameExit= true;
                    break;
                }
            }
            if (nameExit){
                System.err.println("Tên danh mục đã tồn tại, vui lòng nhập lại");
            }else {
                if (this.name.length()>=6&& this.name.length()<=30){
                    checkName = false;
                }else {
                    System.err.println("Tên danh mục phải từ 6-30 ký tự, vui lòng nhập lại");
                }
            }
        }while (checkName);
        // Validate mô tả danh mục
        System.out.println("Nhập vào mô tả danh mục");
        boolean checkDescription = true;
        do {
           this.description = scanner.nextLine();
           if (this.description.trim().equals("")){
               System.err.println("Mô tả danh mục không được để trống");
           }else {
               checkDescription= false;
           }
        }while (checkDescription);
        // Validate trạ
        this.status = Category.inputBoolean(scanner);


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
    public void displayData() {
        String statusDisplay = (this.status)? "Hoạt động" : "Không hoạt động";
        System.out.format("+-------------------------------------------------------------------------------+%n");
        System.out.printf("|");
        System.out.printf("  Mã danh mục  ");
        System.out.printf("|");
        System.out.printf("  Tên danh mục  ");
        System.out.printf("|");
        System.out.printf("  Mô tả                  ");
        System.out.printf("|");
        System.out.printf("  Trạng thái     ");
        System.out.printf("|\n");
        System.out.format("+--------------------------------------------------------------------------------+%n");
        System.out.printf("|");
        System.out.printf("       %d       ",this.id);
        System.out.printf("|");
        System.out.printf("  %s      ",this.name);
        System.out.printf("|");
        System.out.printf("  %s         ",this.description);
        System.out.printf("|");
        System.out.printf("  %s   ",statusDisplay);
        System.out.printf("|\n");
        System.out.format("+--------------------------------------------------------------------------------+%n");
        System.out.println(Color.BLUE+"********************************************************************"+Color.ANSI_RESET);
    }
}
