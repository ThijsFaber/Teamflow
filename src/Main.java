// alle imports die we nodig hebben voor SQL stuff en input van gebruiker
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.sql.Date;
import java.time.LocalDate;

public class Main {

    // deze methode laat je actieve sprints zien en daarna kan je berichten bekijken/sturen
    private static void viewActiveSprints(SprintService sprintService, MessageService messageService, Gebruiker gebruiker, Scanner scanner) throws SQLException {
        List<Sprint> activeSprints;
        //voor scrummasters alle sprints laten zien en voor gebruikers alleen met toegang
        if (gebruiker.getRol().equalsIgnoreCase("scrummaster")) {
            activeSprints = sprintService.getActiveSprints();
        } else {
            activeSprints = sprintService.getActiveSprintsForUser(gebruiker);
        }

        if (activeSprints.isEmpty()) {
            System.out.println("Er zijn momenteel geen actieve sprints."); // lege sprints
            return;
        }

        System.out.println("Actieve sprints:");
        for (Sprint sprint : activeSprints) {
            // print id, naam en hoeveel dagen nog over
            System.out.println(sprint.getSprintID() + ". " + sprint.getNaam() + " - " + sprint.getDaysLeft() + " dagen te gaan");
        }

        System.out.print("Kies een sprint (voer SprintID in): ");
        int sprintID = Integer.parseInt(scanner.nextLine());

        Sprint selectedSprint = null;
        for (Sprint sprint : activeSprints) {
            if (sprint.getSprintID() == sprintID) {
                selectedSprint = sprint;
                break;
            }
        }

        if (selectedSprint != null) {
            System.out.println("Je hebt de sprint '" + selectedSprint.getNaam() + "' gekozen.");
            messageService.displayMessagesForSprint(sprintID);  // hier laat je alle berichten zien die bij deze sprint horen
            sendMessageInSprint(selectedSprint, gebruiker, messageService, scanner); // doorsturen naar volgende class
        } else {
            System.out.println("Ongeldige SprintID. Probeer opnieuw.");
        }
    }

    // alles wat je binnen een sprint kan doen (berichten, threads)
    private static void sendMessageInSprint(Sprint sprint, Gebruiker gebruiker, MessageService messageService, Scanner scanner) {
        ThreadService threadService = new ThreadService(); // thresdservice aanmaken

        while (true) {
            // menu
            System.out.println("\nWat wil je doen in deze sprint?");
            System.out.println("1. Bericht sturen");
            System.out.println("2. Thread checken");
            System.out.println("3. Thread aanmaken");
            System.out.println("4. Zoeken");
            System.out.println("0. Terug");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Typ je bericht: ");
                    String tekst = scanner.nextLine();

                    System.out.println("Wil je een Epic, User Story, of Taak koppelen? (Epic/User Story/Taak/Nee)");
                    String linkChoice = scanner.nextLine();

                    int epicID = 0;
                    int userStoryID = 0;
                    int taakID = 0;

                    // kijken of je iets wilt koppelen
                    if (linkChoice.equalsIgnoreCase("Epic")) {
                        System.out.print("Geef de Epic ID in: ");
                        epicID = Integer.parseInt(scanner.nextLine());
                    } else if (linkChoice.equalsIgnoreCase("User Story")) {
                        System.out.print("Geef de User Story ID in: ");
                        userStoryID = Integer.parseInt(scanner.nextLine());
                    } else if (linkChoice.equalsIgnoreCase("Taak")) {
                        System.out.print("Geef de Taak ID in: ");
                        taakID = Integer.parseInt(scanner.nextLine());
                    }

                    // nu sturen we het bericht naar de database
                    messageService.sendMessage(gebruiker.getGebruikerID(), sprint.getSprintID(), tekst, epicID, userStoryID, taakID);
                    break;

                case "2":
                    try {
                        List<Thread> threads = threadService.getThreadsBySprint(sprint.getSprintID());
                        if (threads.isEmpty()) {
                            System.out.println("Geen threads gevonden in deze sprint.");
                        } else {
                            System.out.println("\nThreads in deze sprint:");
                            for (Thread thread : threads) {
                                System.out.println(thread);
                            }

                            System.out.print("Wil je een thread openen om berichten te bekijken of te reageren? (Voer ThreadID in of 0 om terug te gaan): ");
                            int chosenThreadID = Integer.parseInt(scanner.nextLine());

                            Thread gekozenThread = null;
                            for (Thread t : threads) {
                                if (t.getID() == chosenThreadID) {
                                    gekozenThread = t;
                                    break;
                                }
                            }

                            if (chosenThreadID > 0 && gekozenThread != null) {
                                messageService.displayMessagesInThread(chosenThreadID);
                                if (threadService.isThreadGesloten(gekozenThread.getID())) {
                                    System.out.println("Deze thread is gesloten. Je kunt geen berichten meer sturen.");
                                    break;
                                }


                                boolean inThreadMenu = true;
                                while (inThreadMenu) {
                                    System.out.println("\nKies een optie:");
                                    System.out.println("1. Bericht sturen");
                                    if (gebruiker.getRol().equalsIgnoreCase("scrummaster") || gebruiker.getGebruikerID() == gekozenThread.getMakerID()) {
                                        System.out.println("2. Juiste antwoord kiezen");
                                        System.out.println("3. Thread sluiten");
                                    }
                                    System.out.println("0. Terug");

                                    String keuze = scanner.nextLine();
                                    switch (keuze) {
                                        case "1":
                                            System.out.print("Typ je bericht: ");
                                            tekst = scanner.nextLine();
                                            epicID = userStoryID = taakID = 0;

                                            System.out.print("Koppel een Epic, UserStory of Taak? (Epic/UserStory/Taak/Nee): ");
                                            String koppeling = scanner.nextLine();

                                            if (koppeling.equalsIgnoreCase("Epic")) {
                                                System.out.print("Epic ID: ");
                                                epicID = Integer.parseInt(scanner.nextLine());
                                            } else if (koppeling.equalsIgnoreCase("UserStory")) {
                                                System.out.print("UserStory ID: ");
                                                userStoryID = Integer.parseInt(scanner.nextLine());
                                            } else if (koppeling.equalsIgnoreCase("Taak")) {
                                                System.out.print("Taak ID: ");
                                                taakID = Integer.parseInt(scanner.nextLine());
                                            }

                                            messageService.sendMessageToThread(gebruiker.getGebruikerID(), gekozenThread.getID(), tekst, epicID, userStoryID, taakID);
                                            break;

                                        case "2":
                                            if (gebruiker.getRol().equalsIgnoreCase("scrummaster") || gebruiker.getGebruikerID() == gekozenThread.getMakerID()) {
                                                System.out.print("Vul BerichtID van het juiste antwoord in: ");
                                                int berichtID = Integer.parseInt(scanner.nextLine());
                                                try {
                                                    threadService.setJuisteAntwoord(gekozenThread.getID(), berichtID);
                                                    System.out.println("Juiste antwoord ingesteld!");
                                                } catch (SQLException e) {
                                                    System.err.println("Fout bij instellen van juiste antwoord: " + e.getMessage());
                                                }
                                            } else {
                                                System.out.println("Geen toestemming om dit te doen.");
                                            }
                                            break;

                                        case "3":
                                            if (gebruiker.getRol().equalsIgnoreCase("scrummaster") || gebruiker.getGebruikerID() == gekozenThread.getMakerID()) {
                                                try {
                                                    threadService.sluitThread(gekozenThread.getID());
                                                    System.out.println("Thread succesvol gesloten.");
                                                } catch (SQLException e) {
                                                    System.err.println("Fout bij sluiten van thread: " + e.getMessage());
                                                }
                                                inThreadMenu = false;
                                            } else {
                                                System.out.println("Je hebt geen rechten om deze thread te sluiten.");
                                            }
                                            break;


                                        case "0":
                                            inThreadMenu = false;
                                            break;

                                        default:
                                            System.out.println("Ongeldige keuze.");
                                    }
                                }
                            }
                        }
                    } catch (SQLException e) {
                        System.err.println("Fout bij ophalen van threads: " + e.getMessage());
                    }
                    break;


                case "3":
                    // nieuwe thread maken
                    System.out.print("Titel van de thread: ");
                    String titel = scanner.nextLine();

                    epicID = 0;
                    userStoryID = 0;
                    taakID = 0;

                    System.out.println("Wil je deze thread koppelen aan een Epic, User Story of Taak? (Epic/User Story/Taak/Nee)");
                    String koppeling = scanner.nextLine();

                    if (koppeling.equalsIgnoreCase("Epic")) {
                        System.out.print("Voer Epic ID in: ");
                        epicID = Integer.parseInt(scanner.nextLine());
                    } else if (koppeling.equalsIgnoreCase("User Story")) {
                        System.out.print("Voer User Story ID in: ");
                        userStoryID = Integer.parseInt(scanner.nextLine());
                    } else if (koppeling.equalsIgnoreCase("Taak")) {
                        System.out.print("Voer Taak ID in: ");
                        taakID = Integer.parseInt(scanner.nextLine());
                    }

                    try {
                        threadService.createThread(titel, sprint.getSprintID(), epicID, userStoryID, taakID, gebruiker.getGebruikerID());
                    } catch (SQLException e) {
                        System.err.println("Fout bij het aanmaken van de thread: " + e.getMessage());
                    }
                    break;

                case "4":

                        System.out.println("1. Zoeken op bericht");
                        System.out.println("2. Zoeken op titel");
                        String keuzeZoeker = scanner.nextLine();
                        switch (keuzeZoeker) {
                            case "1":
                                System.out.println("Geef zoekterm");
                                String zoekInhoud = scanner.nextLine();
                                messageService.searchMessageContent(gebruiker.getGebruikerID(), zoekInhoud, sprint.getSprintID());
                                break;

                            case "2":
                                System.out.println("Geef zoekterm");
                                String zoekTitel = scanner.nextLine();
                                messageService.searchMessageTitel(gebruiker.getGebruikerID(), zoekTitel, sprint.getSprintID());
                                break;

                            case "0":
                                break;

                            default:
                                System.out.println("Ongeldige keuze. Probeer opnieuw.");
                        }

                case "0":
                    // terug naar vorige menu
                    return;

                default:
                    System.out.println("Ongeldige keuze. Probeer opnieuw.");
            }
            }
    }
    public static void alleSprintsTonen() throws SQLException {
        System.out.println("Alle sprints tonen:");
        SprintService sprintService = new SprintService();
        List<Sprint> activeSprints = sprintService.getActiveSprints();
        for (Sprint sprint : activeSprints) {
            System.out.println(sprint.getSprintID() + ". " + sprint.getNaam());
        }
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Authenticator auth = new Authenticator(); // login/register handler

        while (true) {
            System.out.println("1. Register\n2. Login\n0. Exit");
            int choice = Integer.parseInt(scanner.nextLine());

            try {
                if (choice == 1) {
                    // registratie
                    System.out.print("Naam: ");
                    String naam = scanner.nextLine();
                    System.out.print("Rol (gebruiker/scrummaster): ");
                    String rol = scanner.nextLine();
                    auth.register(naam, rol);
                    System.out.println("Registratie succesvol.\n");

                } else if (choice == 2) {
                    // login
                    System.out.print("Naam: ");
                    String naam = scanner.nextLine();
                    System.out.print("GebruikerID: ");
                    int id = Integer.parseInt(scanner.nextLine());

                    Gebruiker gebruiker = auth.login(naam, id);
                    if (gebruiker != null) {
                        System.out.println("Welkom " + gebruiker.getNaam() + " (" + gebruiker.getRol() + ")");

                        SprintService sprintService = new SprintService();
                        MessageService messageService = new MessageService();

                        while (true) {
                            System.out.println("\n1. Bekijk actieve sprints");

                            if (gebruiker.getRol().equalsIgnoreCase("scrummaster")) {
                                // extra opties voor scrummaster
                                System.out.println("2. Voeg nieuwe sprint toe");
                                System.out.println("3. Sprint verwijderen");
                                System.out.println("4. Voeg Scrum Elementen toe");
                                System.out.println("5. Voeg een gebruiker aan de sprint toe");
                                System.out.println("6. Verwijder een gebruiker van de sprint");
                            }

                            System.out.println("0. Terug");
                            int opt = Integer.parseInt(scanner.nextLine());

                            if (opt == 1) {
                                viewActiveSprints(sprintService, messageService, gebruiker, scanner);
                            } else if (opt == 2 && gebruiker.getRol().equalsIgnoreCase("scrummaster")) {
                                // nieuwe sprint maken
                                System.out.print("Naam van de sprint: ");
                                String naamSprint = scanner.nextLine();
                                Date datum = Date.valueOf(LocalDate.now());
                                sprintService.addSprint(naamSprint, datum);
                                System.out.println("Nieuwe sprint toegevoegd.");

                            } else if (opt == 3 && gebruiker.getRol().equalsIgnoreCase("scrummaster")) {
                                //sprints verwijderen
                                alleSprintsTonen();
                                System.out.println("Geef sprintID om te verwijderen");
                                int sprintID = Integer.parseInt(scanner.nextLine());
                                Sprint.sprintSluiten(sprintID);
                                System.out.println("Sprint succesvol verwijderd\n");
                            }else if (opt == 4 && gebruiker.getRol().equalsIgnoreCase("scrummaster")) {
                                // scrum elementen toevoegen
                                while (true) {
                                    System.out.println("\n4. Voeg Scrum Elementen toe");
                                    System.out.println("1. Voeg Epic toe");
                                    System.out.println("2. Voeg User Story toe");
                                    System.out.println("3. Voeg Taak toe");
                                    System.out.println("0. Terug");
                                    int scrumOpt = Integer.parseInt(scanner.nextLine());

                                    if (scrumOpt == 1) {
                                        System.out.print("Titel van de Epic: ");
                                        String epicTitle = scanner.nextLine();
                                        System.out.print("Beschrijving van de Epic: ");
                                        String epicDescription = scanner.nextLine();
                                        sprintService.addEpic(epicTitle, epicDescription);
                                    } else if (scrumOpt == 2) {
                                        System.out.print("Titel van de User Story: ");
                                        String usTitle = scanner.nextLine();
                                        System.out.print("Beschrijving van de User Story: ");
                                        String usDescription = scanner.nextLine();
                                        System.out.print("Epic ID (bijbehorende Epic): ");
                                        int epicID = Integer.parseInt(scanner.nextLine());
                                        sprintService.addUserStory(usTitle, usDescription, epicID);
                                    } else if (scrumOpt == 3) {
                                        System.out.print("Titel van de Taak: ");
                                        String taskTitle = scanner.nextLine();
                                        System.out.print("Beschrijving van de Taak: ");
                                        String taskDescription = scanner.nextLine();
                                        System.out.print("User Story ID (bijbehorende User Story): ");
                                        int userStoryID = Integer.parseInt(scanner.nextLine());
                                        sprintService.addTask(taskTitle, taskDescription, userStoryID);
                                    } else if (scrumOpt == 0) {
                                        break;
                                    } else {
                                        System.out.println("Ongeldige keuze.");
                                    }
                                }

                            } else if (opt == 5 && gebruiker.getRol().equalsIgnoreCase("scrummaster")){
                                alleSprintsTonen();
                                System.out.println("SprintID (Sprint om aan toe te voegen)");
                                int sprintID = Integer.parseInt(scanner.nextLine());
                                System.out.println("Geef gebruikersID");
                                int gebruikersID = Integer.parseInt(scanner.nextLine());
                                sprintService.userAddSprint(sprintID, gebruikersID);

                            } else if (opt == 6 && gebruiker.getRol().equalsIgnoreCase("scrummaster")) {
                                alleSprintsTonen();
                                System.out.println("SprintID (Sprint om aan toe te voegen)");
                                int sprintID = Integer.parseInt(scanner.nextLine());
                                System.out.println("1. Enkele gebruiker verwijderen");
                                System.out.println("2. Alle gebriukers verwijderen");
                                int deleteOpt = Integer.parseInt(scanner.nextLine());
                                while (true) {
                                    if (deleteOpt == 1) {
                                        System.out.println("Geef GebruikersID om te verwijderen");
                                        int gebruikersID = Integer.parseInt(scanner.nextLine());
                                        sprintService.userDeleteSprint(sprintID, gebruikersID);
                                        break;
                                    } else if (deleteOpt == 2) {
                                        sprintService.userDeleteAllSprint(sprintID);
                                        break;
                                    } else {
                                        System.out.println("Ongeldige keuze.");
                                    }
                                }
                            }else if (opt == 0) {
                                break;
                            } else {
                                System.out.println("Ongeldige keuze.");
                            }
                        }
                    } else {
                        System.out.println("Inlog mislukt.\n");
                    }
                } else if (choice == 0) {
                    System.out.println("Tot ziens!");
                    break;
                }
            } catch (SQLException e) {
                System.err.println("Database fout: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Ongeldige invoer.");
            }
        }
        scanner.close();
    }
}