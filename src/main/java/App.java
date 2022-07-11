import consoleMenuService.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import httpUtilities.*;


public class App {
    public static void main(String[] args) throws IOException, InterruptedException {

        MenuService menuService = new MenuService();
        menuService.create();

        int choice;
        do {
            menuService.get("Main").printMenu();
            choice = menuService.get("Main").makeChoice();
            switch (choice) {
                case 1:
                    int choicePet;
                    do {
                        menuService.get("Pet").printMenu();
                        choicePet = menuService.get("Pet").makeChoice();
                        switch (choicePet) {
                            case 1:
                                System.out.print("Введите id : ");
                                Scanner sc11 = new Scanner(System.in);
                                long petId11 = sc11.nextLong();
                                try {
                                    if (CommonUtilities.isObjectExist("pet", petId11) == 200) {
                                        Pet pet = PetService.getPetByID(petId11);
                                        System.out.println(pet);
                                    } else {
                                        System.out.println("Домашнего животного с таким id не существует");
                                    }
                                }
                                catch (Exception e) {
                                    e.printStackTrace();
                                }
                                break;
                            case 2:
                                System.out.print("Введите статус( available, pending, sold) : ");
                                Scanner sc12 = new Scanner(System.in);
                                String petStaus = sc12.nextLine();
                                try {
                                        List<Pet> pets = new ArrayList<>();
                                        pets = PetService.getPetsByStatus(petStaus);
                                        System.out.println(pets.toString());
                                }
                                catch (Exception e) {
                                    e.printStackTrace();
                                }
                                break;
                            case 3:
                                System.out.println("Введите, пожалуйста, данные по домашнему животному.");
                                Pet newPet = PetService.inputAllDataOfPet();
                                try {
                                    CommonUtilities.createNewObject("pet", newPet);
                                }
                                catch (Exception e) {
                                    e.printStackTrace();
                                }
                                break;
                            case 4:
                                System.out.println("Для добавления фото, внесите необходимые данные)");
                                System.out.print("id домашнего животного : ");
                                Scanner sc14 = new Scanner(System.in);
                                long idPet14 = sc14.nextLong();
                                System.out.print("Введите дополнительные данные для передачи на сервер : ");
                                String additionalMetedata = sc14.nextLine();
                                if( additionalMetedata.equals("")) additionalMetedata = sc14.nextLine();
                                System.out.print("Для того, чтобы загрузить картинку, поместите файл в корневой каталог программы" +
                                        " и укажите его имя (имя.расширение) : ");
                                String photoUrl14 = sc14.nextLine();
                                try {
                                    if(200 == PetService.addPhotoToPet(idPet14, photoUrl14, additionalMetedata)) {
                                        System.out.println("картинка успешно добавлена");
                                        Pet pet = PetService.getPetByID(idPet14);
                                        System.out.println(pet);
                                        if (pet.getPhotoUrls() != null ) {
                                            System.out.println(pet.getPhotoUrls().toString());
                                         } else {
                                            System.out.println("[]");
                                        }
                                        pet.getPhotoUrls().add(photoUrl14);
                                        System.out.println(pet.getPhotoUrls().toString());
                                        CommonUtilities.updateObject("pet", pet);
                                    };
                                }
                                catch (Exception e) {
                                    e.printStackTrace();
                                }
                                break;
                            case 5:
                                System.out.print("id домашнего животного : ");
                                Scanner sc15 = new Scanner(System.in);
                                long idPet15 = sc15.nextLong();
                                System.out.println("Введите, пожалуйста, те данные по домашнему животному, которого желаете обновить. А именно : ");
                                System.out.print("новая кличка : ");
                                String namePet15 = sc15.nextLine();
                                if(namePet15.equals("")) namePet15 = sc15.nextLine();
                                System.out.print("новый статус домашнего животного (available,pending,sold) : ");
                                String petStatus15 = sc15.nextLine();
                                if  (PetService.updatePetByFormData(idPet15, namePet15, petStatus15) == 200) {
                                    System.out.println("Данные успешно обновлены");
                                    Pet petUpdatedByFormData = PetService.getPetByID(idPet15);
                                    System.out.println(petUpdatedByFormData);
                                } else {
                                    System.out.println("Данные не изменены. Скорее всего был некорректный ввод данных");
                                }
                                break;
                            case 6:
                                System.out.println("Введите, пожалуйста, все данные по домашнему животному, которого желаете обновить.");
                                Pet petToUpdateAllData = PetService.inputAllDataOfPet();
                                CommonUtilities.updateObject("pet", petToUpdateAllData);
                                break;
                            case 7:
                                System.out.println("Введите, пожалуйста, данные по домашнему животному, которого желаете удалить из базы.");
                                Scanner sc17 = new Scanner(System.in);
                                System.out.print("арi ключ : ");
                                String apiKey = sc17.nextLine();
                                System.out.print("id домашнего животного : ");
                                long idPet17 = sc17.nextLong();
                                Pet petToDelete = PetService.getPetByID(idPet17);
                                CommonUtilities.deleteObject("pet/" + idPet17, petToDelete);
                        }

                    } while (choicePet != 8);
                    break;
                case 2:
                    int choiceStore;
                    do {
                        menuService.get("Store").printMenu();
                        choiceStore = menuService.get("Store").makeChoice();
                        switch (choiceStore) {
                            case 1:
                                StoreService.getInventory();
                                break;
                            case 2:
                                System.out.println("Обратите внимание, что номер заказа должен быть в пределах от 1 до 10. иначе будет выброшена ошибка ввода.");
                                System.out.print("Введите id : ");
                                Scanner sc22 = new Scanner(System.in);
                                long orderId22 = sc22.nextLong();
                                try {
                                    if (CommonUtilities.isObjectExist("store/order", orderId22) == 200) {
                                        Order order = StoreService.getOrderByID("order", orderId22);
                                        System.out.println(order);
                                    } else {
                                        System.out.println("Заказа с таким id не существует");
                                    }
                                }
                                catch (Exception e) {
                                    e.printStackTrace();
                                }
                                break;
                            case 3:
                                System.out.println("Введите, пожалуйста, данные по заказу, который надо разместить.");
                                Order newOrder = StoreService.inputAllDataOfOrder();
                                try {
                                    CommonUtilities.createNewObject("store/order", newOrder);
                                }
                                catch (Exception e) {
                                    e.printStackTrace();
                                }
                                break;
                            case 4:
                                System.out.print("Введите, пожалуйста, данные по заказу, который желаете удалить из базы.");
                                Scanner sc24 = new Scanner(System.in);
                                System.out.print("id домашнего животного (1 - 9) : ");
                                long orderId24 = sc24.nextLong();
                                CommonUtilities.deleteObject("store/order/" + orderId24, StoreService.getOrderByID("order", orderId24));
                        }
                    } while (choiceStore != 5);
                    break;
                case 3:
                    int choiceUser;
                    do {
                        menuService.get("User").printMenu();
                        choiceUser = menuService.get("User").makeChoice();
                        switch (choiceUser) {
                            case 1:
                                //  https://www.baeldung.com/httpclient-post-http-request
                                System.out.print("Введите имя пользователя : ");
                                Scanner sc31 = new Scanner(System.in);
                                String nameForLogIn = sc31.nextLine();
                                System.out.print("Введите пароль пользователя : ");
                                String passwordForLogIn = sc31.nextLine();
                                UserService.logsUser(nameForLogIn, passwordForLogIn);
                                break;
                            case 2:
                                UserService.logsOutUser();
                                break;
                            case 3:
                                System.out.print("Введите userName пользователя : ");
                                Scanner sc33 = new Scanner(System.in);
                                String userName33 = sc33.nextLine();
                                try {
                                    if (CommonUtilities.isObjectExist("user", userName33) == 200) {
                                        User user = UserService.getUserByUsername(userName33);
                                        System.out.println(user);
                                    } else {
                                        System.out.println("Пользователя с таким именем не существует");
                                    }
                                }
                                catch (Exception e) {
                                    e.printStackTrace();
                                }
                                break;
                            case 4:
                                System.out.println("Введите, пожалуйста, данные по пользователю.");
                                User newUser = UserService.inputAllDataOfUser();
                                try {
                                    CommonUtilities.createNewObject("user", newUser);
                                }
                                catch (Exception e) {
                                    e.printStackTrace();
                                }
                                break;
                            case 5:

                                break;
                            case 6:
                                System.out.print("Введите, пожалуйста, имя пользователя, которого желаете обновить : ");
                                Scanner sc36 = new Scanner(System.in);
                                String nameToUpdate = sc36.nextLine();
                                try {
                                    if (CommonUtilities.isObjectExist("user", nameToUpdate) == 200) {
                                        System.out.println("Введите, пожалуйста, все данные по пользователю, которого желаете обновить.");
                                        User userToUpdate = UserService.inputAllDataOfUser();
                                        CommonUtilities.updateObject("user/" + nameToUpdate, userToUpdate);
                                    } else {
                                        System.out.println("Пользователя с таким именем не существует");
                                    }
                                }
                                catch (Exception e) {
                                    e.printStackTrace();
                                }
                                break;
                            case 7:
                                System.out.print("Введите, пожалуйста, имя пользователя, которого желаете удалить из системы : ");
                                Scanner sc37 = new Scanner(System.in);
                                String nameToDelete = sc37.nextLine();
                                try {
                                    if (CommonUtilities.isObjectExist("user", nameToDelete) == 200) {
                                        User userToDelete = UserService.getUserByUsername(nameToDelete);
                                        CommonUtilities.deleteObject("user/" + nameToDelete, userToDelete);
                                    } else {
                                        System.out.println("Пользователя с таким именем не существует");
                                    }
                                }
                                catch (Exception e) {
                                    e.printStackTrace();
                                }
                        }
                    } while (choiceUser != 8);
                    break;

            }
        } while (choice != 4);
    }
}
