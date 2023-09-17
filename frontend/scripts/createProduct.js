//NICO//

// hier wird dem Element mit der Id "createProductButton" ein listener
// für das onclick-Event hinzugefügt
$("#createProductButton").on("click", _e => {
    // entfernen der input-error class von allen elementen,
    // die die input-error class besitzen
    $(".input-error").removeClass("input-error");

    // löscht alle elemente mit der klasse error-message
    $(".error-message").remove();

    // wir erstellen ein object und setzen die 
    // Werte aus den Eingabefeldern
    const product = {
        "name": $("#nameInput").val(),
        "description": $("#descriptionInput").val(),
        "imageUrl": $("#imageInput").val(),
        "price": $("#priceInput").val(),
        "quantity": $("#quantityInput").val(),
        "type": $("#typeInput").val(),
    }

    // dann senden wir eine POST request mit dem neuen
    // product als json string an das backend
    $.ajax({
        url: "http://localhost:8080/products",
        type: "POST",
        cors: true,
        headers: { "Authorization": sessionStorage.getItem("token") },
        contentType: "application/json",
        data: JSON.stringify(product),
        success: console.log,
        error: error => {
            console.log(error);
            if (error.status === 400) {

                // über alle Fehler iterieren
                for (let err of error.responseJSON.errors) {
                    // das inputfeld entsprechend dem Fehler raussuchen
                    const input = $("#" + err.field + "Input");
                    // klasse für die anzeige des fehlerhaften feldes hinzufügen
                    input.addClass("input-error");

                    // den parent des inputfeldes raussuchen
                    const parent = input.parent();
                    // anfügen der fehlermeldung im parent
                    parent.append(`<p class="error-message">${err.defaultMessage}</p>`);
                }

                // aufruf der Funktion, damit die Toasts angezeigt werden
                handleErrors(error.responseJSON.errors);
            }
        }
    });
});
