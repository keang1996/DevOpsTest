function getOrderCart() {
  let orderPayloadStr = sessionStorage.getItem('orderPayload');
  let str = ''
  let totalPrice = 0
  let totalPriceStr = ''
  let orderCartList = [];
  let orderCart = {
    productId: '',
    quantity: '',
  }
  if (orderPayloadStr != null) {
    let orderPayloadDecode = atob(orderPayloadStr);
    let orderPayload = JSON.parse(orderPayloadDecode);
    for (let i = 0; i < orderPayload.length; i++) {
      str += '<li>'
      str += '<div class="community-post">'
      //------------------ รูป --------------------------
      str += '<div class="author-avator set-bg">'
      str += ' <img class="cart-photo set-bg" src="' + orderPayload[i].image
          + '" >'
      str += '</div>'
      //------------------ รูป --------------------------
      //------------------ รายละเอียด --------------------------
      str += ' <div class="post-content"> '
      str += ' <h5>' + orderPayload[i].productName + '</h5>'
      str += '</div>'
      //------------------ รายละเอียด --------------------------
      str += '<div class="post-content text-right">'
      str += '<div class="quantity">'
      str += ' <h5>'
      str += '<button id="minus-cart-' + orderPayload[i].productId
          + '" onclick="cartsMinus(\'cart-' + orderPayload[i].productId
          + '\')" type="button" name="cart-' + orderPayload[i].productId
          + '" class="btn btn-item button-item-event">'
      str += '-'
      str += '</button>&nbsp;'
      str += '<input id="cart-' + orderPayload[i].productId
          + '" type="text" name="name" value="' + orderPayload[i].quantity
          + '" min="0" height="20px" maxlength="6" class="input-item" onkeypress=" return isNumber(event)" onblur="cartPageKeypress(\'cart-'
          + orderPayload[i].productId + '\',event)" />&nbsp;'
      str += '<button id="plus-cart-' + orderPayload[i].productId
          + '" onclick="cartsPlus(\'cart-' + orderPayload[i].productId
          + '\')" type="button" name="cart-' + orderPayload[i].productId
          + '" class="btn btn-item button-item-event"> '
      str += '+'
      str += '</button>&nbsp;'
      str += 'ราคา : ' +
          parseFloat(orderPayload[i].price).toLocaleString("th-TH")
          + ' บาท'
      str += '</h5>'
      str += '</div>'
      str += '</div>'
      str += '</div>'
      str += '</li>'
      totalPrice += parseInt(orderPayload[i].totalPrice);
      orderCart = {
        productId: '',
        quantity: '',
      }
      orderCart.productId = orderPayload[i].productId.replace("product", "");
      orderCart.quantity = orderPayload[i].quantity
      orderCartList.push(orderCart);
    }
  } else {
    str += '<li style="height: 100px;">'
    str += ' <div class="post-content"> '
    str += ' <h5 style="text-align: center;">ไม่มีสินค้าในตะกร้า</h5>'
    str += '</div>'
    str += '</li>'
    document.getElementById("showDetail").style.display = "none";
  }
  totalPriceStr += '<h5> ราคารวม : ' + totalPrice.toLocaleString("th-TH"); + ' บาท</h5>'
  document.getElementById("orderProduct").innerHTML = str
  document.getElementById("totalPriceProduct").innerHTML = totalPriceStr

  let checkPayment = document.getElementById("checkPayment").textContent;

  if (checkPayment == 'no') {
    if (totalPrice > 0) {
      document.getElementById("paymentButton").style.display = "block";
    } else {
      document.getElementById("paymentButton").style.display = "none";
    }
  }

  let cartSide = document.getElementById("button-cart-side-nav");
  cartSide.style.display = "none";

  if (checkPayment == 'yes') {
    disabledButton();
    sessionStorage.removeItem('orderPayload')
  } else {
    document.getElementById("orderCart").value = JSON.stringify(orderCartList)
  }
}

let stompClient = null;

// function connect() {
//     disconnect();
//     let socket = new SockJS('/greetings');
//     stompClient = Stomp.over(socket);
//     stompClient.debug = null
//     let steamId = document.getElementById('steam_id').getAttribute('value');
//     stompClient.connect({"user": steamId}, function (frame) {
//         stompClient.subscribe('/user/queue/greetings', function (greeting) {
//             let orderRef = JSON.parse(greeting.body).orderRef;
//             console.log(orderRef);
//             new Notify({
//                 title: 'ข้อความจากระบบ',
//                 text: 'หมายเลขคำสั่งซื้อ #' + orderRef + ' ชำระเงินสำเร็จ',
//                 status: 'success',
//                 showIcon: true,
//                 autoclose: true,
//                 autotimeout: 10000
//             })
//             sessionStorage.removeItem('orderPayload');
//             getCartSession();
//             getOrderCart();
//             window.location="/";
//         });
//     });
// }

// function disconnect() {
//     console.log('Disconected')
//     if (stompClient != null) {
//         stompClient.disconnect();
//     }
// }
//
// function testClick() {
//     new Notify({
//         title: 'Notify Title',
//         text: 'Notify Message',
//         status: 'success',
//         showIcon: true,
//         autoclose: true,
//         autotimeout: 3000
//     })
// }

function disabledButton() {
  let orderPayloadStr = sessionStorage.getItem('orderPayload');
  if (orderPayloadStr != null) {
    let orderPayloadDecode = atob(orderPayloadStr);
    let orderPayload = JSON.parse(orderPayloadDecode);
    for (let i = 0; i < orderPayload.length; i++) {
      const minus = "minus-cart-" + orderPayload[i].productId;
      const plus = "plus-cart-" + orderPayload[i].productId;
      document.getElementById(plus).remove();
      document.getElementById(minus).remove();

      const price = "cart-" + orderPayload[i].productId;
      const priceElement = document.getElementById(price);
      priceElement.value = "จำนวน " + orderPayload[i].quantity
          + " ชิ้น     ชิ้นละ";
      priceElement.style.width = "180px";
      priceElement.style.height = "40px";
      priceElement.readOnly = true;
      $('#' + price).removeAttr('onblur');
      priceElement.classList.remove("input-item");
    }
  }
}

function cartPageKeypress(productId, event) {
  //let total = document.getElementById(productId).value
  let total = event.target.value;
  let totalInt = 1;
  let action = '';
  if (parseInt(total) >= 1) {
    totalInt = parseInt(total)
  } else {
    $("#deleteItemModal").modal();
    document.getElementById(productId).value = total;
    document.getElementById("deleteItemId").value = productId
    return
  }
  document.getElementById(productId).value = totalInt.toString()
  productId = productId.replace('cart-', '');
  editProductCart(productId, totalInt, action);

}
