public class lab6 {

    public static LinkedList initialize_deck() {

        LinkedList deck = new LinkedList();

        // populate linked list with a single deck of cards
        for (Card.suites s : Card.suites.values()) {
            for(Card.ranks r : Card.ranks.values()) {
                if (r != Card.ranks.NULL && s != Card.suites.NULL) {
                    Card newCard = new Card(s, r);
                    //newCard.print_card();
                    deck.add_at_tail(newCard);
                }
            }
        }

        return deck;
    }

    private static void play_blind_mans_bluff(LinkedList player1, LinkedList computer, LinkedList deck) {
        System.out.println("\nStarting Blind mans Bluff \n");

        int lossStreak = 0; // Track consecutive losses
        int totalWins = 0;
        int totalLosses = 0;

        for (int i = 0; i < 5; i++) { // Play 5 rounds
            Card playerCard = player1.remove_from_head();
            Card computerCard = computer.remove_from_head();

            System.out.print("Opponent's card: ");
            computerCard.print_card();
            System.out.println();

            System.out.print("Guess if your card is Higher (H) or Lower (L): ");
            char guess = new java.util.Scanner(System.in).next().toUpperCase().charAt(0);

            boolean playerWins = compareCards(playerCard, computerCard, guess);

            if (playerWins) {
                System.out.println("You win this round!");
                lossStreak = 0; // Reset loss streak
                totalWins++;
            } else {
                System.out.println("You lost this round.");
                lossStreak++;
                totalLosses++;
            }

            // Add the cards back into the deck
            deck.add_at_tail(playerCard);
            deck.add_at_tail(computerCard);

            // Check if Player 1 has lost 3 rounds in a row
            if (lossStreak >= 3) {
                rage_quit(deck, player1, computer);
                lossStreak = 0; // Reset streak after rage quit
            }
        }

        System.out.println("\nGame Over!");
        System.out.println("Total Wins: " + totalWins);
        System.out.println("Total Losses: " + totalLosses);
    }

    private static boolean compareCards(Card playerCard, Card computerCard, char guess) {
        // Create a rank order manually
        // Create a rank order
        java.util.Map<Card.ranks, Integer> rankOrder = new java.util.HashMap<>();
        int order = 0;
        for (Card.ranks r : Card.ranks.values()) {
            rankOrder.put(r, order++);
        }

        int playerRank = rankOrder.get(playerCard.getRank());
        int computerRank = rankOrder.get(computerCard.getRank());

        if (playerRank > computerRank) {
            return guess == 'H'; // Player guessed higher
        } else if (playerRank < computerRank) {
            return guess == 'L'; // Player guessed lower
        } else {
            // If ranks are equal, break tie with suits
            java.util.Map<Card.suites, Integer> suitOrder = new java.util.HashMap<>();
            int suitOrderValue = 0;
            for (Card.suites s : Card.suites.values()) {
                suitOrder.put(s, suitOrderValue++);
            }
            int playerSuit = suitOrder.get(playerCard.getSuit());
            int computerSuit = suitOrder.get(computerCard.getSuit());
            return (playerSuit > computerSuit && guess == 'H') || (playerSuit < computerSuit && guess == 'L');
        }
    }


    private static void rage_quit(LinkedList deck, LinkedList player1, LinkedList computer) {
        System.out.println("\nPlayer 1 has lost 3 rounds in a row! Time to rage quit...\n");

        // Add all cards back into the deck
        while (player1.size > 0) {
            deck.add_at_tail(player1.remove_from_head());
        }
        while (computer.size > 0) {
            deck.add_at_tail(computer.remove_from_head());
        }

        // Shuffle the deck
        deck.shuffle(512);

        // Ensure there are enough cards to redeal
        int num_cards_dealt = 5;
        if (deck.size < num_cards_dealt * 2) {
            System.out.println("Not enough cards left to redeal. Game Over!");
            return;
        }

        // Redeal 5 cards to each player
        for (int i = 0; i < num_cards_dealt; i++) {
            player1.add_at_tail(deck.remove_from_head());
            computer.add_at_tail(deck.remove_from_head());
        }

        System.out.println("New game started after rage quit!");
    }

    public static void main(String[] args) {

        // create a deck (in order)
        LinkedList deck = initialize_deck();
        deck.print();
        deck.sanity_check(); // because we can all use one

        // shuffle the deck (random order)
        deck.shuffle(512);
        deck.print();
        deck.sanity_check(); // because we can all use one

        // cards for player 1 (hand)
        LinkedList player1 = new LinkedList();
        // cards for player 2 (hand)
        LinkedList computer = new LinkedList();

        int num_cards_dealt = 5;
        for (int i = 0; i < num_cards_dealt; i++) {
            // player removes a card from the deck and adds to their hand
            player1.add_at_tail(deck.remove_from_head());
            computer.add_at_tail(deck.remove_from_head());
        }

        // let the games begin!
        play_blind_mans_bluff(player1, computer, deck);
    }
}

