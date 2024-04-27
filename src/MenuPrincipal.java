public class MenuPrincipal extends Menu {

    public MenuPrincipal(String title) {
        super(title);
    }

    @Override
    public void menu() {
        System.out.println(inputDate());
    }
}
