$(document).ready(function () {
  console.log("test");
  $.ajax({
    url: 'http://localhost:8080/products',
    headers: { "Authorization": sessionStorage.getItem("token") },
    success: function (response) {
      addProductsToList(response);
    },
    error: function (error) {
      console.error(error);
    }
  });

  function addProductsToList(products) {
    const productTableBody = $("#productTableBody");

    products.forEach(function (product) {
      const row = $("<tr>");
      row.append($("<td>").text(product.id));
      row.append($("<td>").text(product.name));
      row.append($("<td>").text(product.description));
      row.append($("<td>").text(product.quantity));
      row.append($("<td>").text(product.type));
      row.append($("<td>").text(product.price));
      row.append($("<td>").text(product.active));
      const editButton = $("<td><button id='editButton'>Edit</button></td>");
      editButton.click(function () {
        displayProductForEditing(product);
      });
      row.append(editButton);

      productTableBody.append(row);
    });
  }


});

function displayProductForEditing(product) {
  let productId = product.id;
  let imageId = product.imageUrl;
  let productListContainer = document.getElementById("productListContainer");
  let productEditContainer = document.getElementById("productEditContainer");


  productListContainer.style.display = "none";
  productEditContainer.style.display = "block";

  $("#editProductId").val(productId);
  $("#editProductName").val(product.name);
  $("#editProductDescription").val(product.description);
  $("#editProductQuantity").val(product.quantity);
  $("#editProductType").val(product.type);
  $("#editProductPrice").val(product.price);
  $("#editProductActive").prop("checked", product.active);

  document.getElementById("deleteEditButton").addEventListener("click", function () {
      deleteProduct(productId, imageId);
  });

}

document.getElementById("cancelEditButton").addEventListener("click", function () {
  $("#productEditContainer").hide();
  $("#productListContainer").show();
});

function deleteProduct(productId, imageId) {
  if (confirm("Are you sure you want to delete this product?")) {
    $.ajax({
      url: "http://localhost:8080/products/" + productId,
      method: "DELETE",
      headers: { "Authorization": sessionStorage.getItem("token") },
      success: function (response) {
        console.log("Deleted product:", response);
        location.reload();

      },
      error: function (xhr, status, error) {
        console.error("Delete request failed. Status:", status, "Error:", error);
      }
    });
  } else {
    location.reload();
  }
}