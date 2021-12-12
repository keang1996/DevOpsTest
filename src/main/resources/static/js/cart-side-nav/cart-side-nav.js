function openNav() {
  if(window.screen.width >= 1024){
    document.getElementById("mySidenav").style.width = "35%";
  }else{
    document.getElementById("mySidenav").style.width = "70%";
  }
  setTimeout(function(){
    document.getElementById("carts").style.display = "block";
  }, 350);
}

function closeNav() {
  document.getElementById("carts").style.display = "none";
  document.getElementById("myCanvasNav").style.width = "0";
  document.getElementById("myCanvasNav").style.opacity = "0";
  document.getElementById("mySidenav").style.width = "0";
}

function openNavShadow() {
    document.getElementById("myCanvasNav").style.width = "100%";
    document.getElementById("myCanvasNav").style.opacity = "0.8";
}

function cartsPlus(productId) {
  let total = document.getElementById(productId).value
  if(parseInt(total) < 999999 ){
      let totalInt = parseInt(total) + 1
      document.getElementById(productId).value = totalInt.toString()
      productId = productId.replace('cart-','');
      editProductCart(productId,totalInt,"plus");
  }else{
      $("#limitItemModal").modal();
  }

}

function cartsMinus(productId) {
  let total = document.getElementById(productId).value
  let totalInt = 1
  let action = ''
  if (parseInt(total) > 1) {
    totalInt = parseInt(total) - 1
  }else{
      $("#deleteItemModal").modal();
      document.getElementById("deleteItemId").value = productId
      return
  }
  document.getElementById(productId).value = totalInt.toString()
  productId = productId.replace('cart-','');
  editProductCart(productId,totalInt,action);
}

function deleteItem(){
    let productId = document.getElementById("deleteItemId").value
    let totalInt = 1
    const action = 'delete'
    document.getElementById(productId).value = totalInt.toString()
    productId = productId.replace('cart-','');
    editProductCart(productId,totalInt,action);
}

function getCartSession(){
  let orderPayloadStr = sessionStorage.getItem('orderPayload');
  let str = '';
  let totalPrice = 0;
  if(orderPayloadStr != null){
    let orderPayloadDecode = atob(orderPayloadStr);
    const orderPayload = JSON.parse(orderPayloadDecode);
    for(let i = 0 ; i < orderPayload.length; i++){
      str += '<div class="row">'
      // ส่วนของรูป --------------------------------------------------
      str += '<div class="col-lg-4 col-md-12"> '
      str += '<div class="cart-author-avator">'
      str += ' <img class="cart-photo set-bg" src="'+orderPayload[i].image+'" >'
      str += '</div>'
      str += '</div>'
      // ส่วนของรูป --------------------------------------------------
      // ส่วนของรายละเอียด --------------------------------------------------
      str += '<div id="text-cart" class="col-lg-4 col-md-12 cart-text">'
      str += '<h5>'+orderPayload[i].productName+'</h5>'
      str += '</div>'
       // ส่วนของรายละเอียด --------------------------------------------------
       // ส่วนของจำนวน --------------------------------------------------
       str += '<div class="col-lg-4 col-md-12 cart-quantity" style="text-align: center">'
       str += '<div class="quantity-cart" style="display: inline-flex;">'
       str +=  '<div>'
       str += '<button onclick="cartsMinus(\'cart-'+orderPayload[i].productId+'\')" type="button" name="cart-'+orderPayload[i].productId+'" class="btn btn-item button-item-event">'
       str += '-'
       str += '</button>'
       str +=  '&nbsp;</div>'
       str +=  '<div>'
       str += '<input id="cart-'+orderPayload[i].productId+'" type="text" name="name" value="'+orderPayload[i].quantity+'" min="0" height="20px" maxlength="6" onkeypress=" return isNumber(event)" onblur="cartsKeypress(\'cart-'+orderPayload[i].productId+'\')" />'
       str +=  '&nbsp;</div>'
       str +=  '<div>'
       str += '<button onclick="cartsPlus(\'cart-'+orderPayload[i].productId+'\')" type="button" name="cart-'+orderPayload[i].productId+'" class="btn btn-item button-item-event"> '
       str += '+'
       str += '</button>'
       str +=  '</div>'
       str += '</div>'
       str += '<div>'
       str += '<h5>ราคา : '+parseFloat(orderPayload[i].price).toLocaleString("th-TH")+' บาท</h5>'
       str += '</div>'
       str += '</div>'
      // ส่วนของจำนวน --------------------------------------------------
       str += '</div>'
       totalPrice += parseInt(orderPayload[i].totalPrice)
    }
    str += '<div class="row">'
    str += '<div class="col-lg-12 col-md-12 cart-payment-button">'
    str += '<a href="/cart" >'
    str += '<button class="button-item-cart button-item-event" style="width: 100%;" type="button">'
    str += 'ไปที่ตะกร้าสินค้า ･ ราคารวม : '+totalPrice.toLocaleString('th-TH')+' บาท'
    str += ' </button>'
    str += '</a>'
    str += '</div>'
    str += '</div>'
  }else{
    str += '<h5 style="text-align: center;">ไม่มีสินค้าในตะกร้า</h5>'
  }


  document.getElementById("carts").innerHTML = str;
}

function editProductCart(productId,quantity,action) {
  let orderPayloadModel = {
    productId: '',
    productName: '',
    quantity: '',
    price:'',
    totalPrice: '',
    image:''
  }
  let orderPayloadStr = sessionStorage.getItem('orderPayload');
  let orderPayloadDecode = atob(orderPayloadStr);
  let orderPayload = JSON.parse(orderPayloadDecode);

  if (orderPayload != null) {
    for (let i = 0; i < orderPayload.length; i++) {
      if (orderPayload[i].productId == productId) {
        if(action == 'delete'){
          orderPayload.splice(i, 1);
          break
        }
        // checkProduct = false
        orderPayloadModel = orderPayload[i]
        orderPayload.splice(i, 1);
        orderPayloadModel.productId = productId
        orderPayloadModel.quantity = quantity
        orderPayloadModel.totalPrice = (orderPayloadModel.price * quantity)
        orderPayload.push(orderPayloadModel)
        break
      }
    }
  }
  orderPayload.sort((a,b)=> (a.productId > b.productId ? 1 : -1))
  if(orderPayload.length == 0){
    sessionStorage.removeItem('orderPayload');
  }else{
    sessionStorage.setItem('orderPayload', btoa(JSON.stringify(orderPayload)));
  }
  getCartSession();
  const checkPage = window.location.href;
  if(checkPage.indexOf('cart') != -1){
      getOrderCart();
  }
}
function isNumber(evt) {
    evt = (evt) ? evt : window.event;
    let charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
        return false;
    }
    return true;
}

function cartsKeypress (productId) {
    let total = document.getElementById(productId).value
    let totalInt = 1
    let action = ''
    if (parseInt(total) >= 1) {
        totalInt = parseInt(total)
    }else{
        $("#deleteItemModal").modal();
        document.getElementById("deleteItemId").value = productId
        return
    }
    document.getElementById(productId).value = totalInt.toString()
    productId = productId.replace('cart-','');
    editProductCart(productId,totalInt,action);
}

function checkQuantity(){
    let productId = document.getElementById("deleteItemId").value
    let totalInt = 1
    let checkProductId = document.getElementById(productId);
    if(checkProductId != null){
        let checkQuantity = document.getElementById(productId).value;
        if(Number(checkQuantity) < 1) {
            const action = '';
            document.getElementById(productId).value = totalInt.toString()
            productId = productId.replace('cart-', '');
            editProductCart(productId, totalInt, action);
        }
    }
}
