# System zarządzający restauracją

## Opis
Celem projektu jest stworzenie aplikacji, która umożliwi prowadzenie restauracji. W tym celu stworzona została baza danych, która pozwoli na przechowywanie informacji między innymi o:
- aktualnie zatrudnionych pracownikach,
- produktach znajdujących się w magazynie,
- zamówieniach, które składają pracownicy,
- daniach, które są zamawiane przez klientów,
- rachunkach, które wystawiają pracownicy.

W ramach projektu powstała aplikacja desktopowa, która po zalogowaniu się, daje dostęp do funkcjonalności w zależności od uprawnień pracownika:
- kucharz ma wgląd w swoje godziny oraz zabiera produkty z magazynu,
- kelner poza uprawnieniami kucharza, może wystawiać rachunki i ma wgląd w swoje rachunki,
- menedżer posiada najwięcej uprawnień: zwalnia i zatrudnia pracowników, wprowadza nowe dania do menu oraz modyfikuje je i usuwa gdy zachodzi taka potrzeba, składa i odbiera zamówienia, decyduje o tym co jest w magazynie, jakie produkty do niego dodać, a jakie wyrzucić. Menedżer może pełnić również rolę kelnera.

Aby zrealizować projekt użyto:
- SZBD ORACLE - baza danych,
- Java,
- JavaFX,
- JDBC - połączenie z bazą danych realizowane w aplikacji.
