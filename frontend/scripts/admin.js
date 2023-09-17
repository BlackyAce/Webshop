document.addEventListener('DOMContentLoaded', function () {
    const formCreateProduct = document.getElementById('createProduct-form');

    formCreateProduct.addEventListener('submit', function (event) {
        event.preventDefault();

        const productName = document.getElementById('inputProductTitel').value;
        const productDescription = document.getElementById('inputProductDescription').value;
        const productQuantity = document.getElementById('inputProductQuantity').value;
        const productType = document.getElementById('inputProductType').value;
        const productPrice = document.getElementById('inputProductPrice').value;

        const fileInput = document.getElementById("inputProductFile");
        const file = fileInput.files[0];

        console.log(file);

        const formData = new FormData();

        if (file) {
            formData.append("file", file);

            $.ajax({
                url: 'http://localhost:8080/files',
                type: 'POST',
                contentType: false,
                processData: false,
                headers: { "Authorization": sessionStorage.getItem("token") },
                data: formData,
                success: function (imageName) {
                    console.log('File erfolgreich gesendet:', imageName);

                    // Hier senden wir die restlichen Produktinformationen an den Server
                    const product = {
                        name: productName,
                        description: productDescription,
                        quantity: productQuantity,
                        type: productType,
                        price: productPrice,
                        file: true,
                        imageUrl: imageName,
                        active: false
                    };

                    


                    $.ajax({
                        url: 'http://localhost:8080/products/create',
                        type: 'POST',
                        contentType: 'application/json',
                        headers: { "Authorization": sessionStorage.getItem("token") },
                        data: JSON.stringify(product),
                        success: function (response) {
                            console.log('Produkt erfolgreich erstellt:', response);
                            alert('Produkt erfolgreich erstellt!');
                            // Manuell das Formular senden, ohne die Seite neu zu laden
                            formCreateProduct.submit();
                        },
                        error: function (xhr, status, error) {
                            console.error('Fehler beim Senden der Produktinformationen:', error);
                        }
                    });
                },
                error: function (xhr, status, error) {
                    console.error('Fehler beim Hochladen der Datei:', error);
                }
            });
            alert("test");
        } else {
            console.log("Keine Datei ausgewählt.");
        }
    });
});
