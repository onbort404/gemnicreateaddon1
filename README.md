# Create: Andesite Mechanica (NeoForge 1.21.1)

Gotowy projekt add-onu do moda **Create** na **NeoForge 1.21.1**, inspirowany estetyką Obudowy Andezytowej (Andesite Casing).

## Zawartość moda:
1. **Wzmocniona obudowa andezytowa (`reinforced_andesite_casing`)** – blok konstrukcyjny odporny na wybuchy, utrzymany w stylistyce ciemnego drewna i stalowo-andezytowej ramy.
2. **Andezytowy przesiewacz kinetyczny (`andesite_sifter`)** – maszyna zasilana od dołu wałem (Shaft) z moda Create. Po rzuceniu na jej górną ściankę żwiru (Gravel) lub piasku (Sand), wykorzystuje siłę obrotową (RPM) do odsiewania krzemienia, bryłek żelaza, bryłek złota, gliny oraz andezytu.

## Jak otworzyć i skompilować projekt:
1. Rozpakuj archiwum `.zip` do wybranego folderu.
2. Otwórz program **IntelliJ IDEA** (z zainstalowaną **Javą 21**).
3. Wybierz **Open** i wskaż wypakowany folder.
4. Poczekaj, aż Gradle pobierze zależności NeoForge 1.21.1 oraz Create.
5. Aby przetestować moda w grze, uruchom konfigurację `runClient`.
6. Aby zbudować gotowy plik `.jar` moda, wykonaj zadanie Gradle: `Tasks -> build -> build` (plik znajdziesz w `build/libs/`).
