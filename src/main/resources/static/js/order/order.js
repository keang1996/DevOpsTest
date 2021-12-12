function init(){
}

function openQrcode(orderId,amount,orderDetailJson){
    const orderDetails = JSON.parse(orderDetailJson);
    let str = '';

    for(let i = 0; i < orderDetails.length ; i++){
        str += '<tr>'
        str += '<th scope="row" style="vertical-align: middle;">'+orderDetails[i].productName +'</th>'
        str += '<td style="text-align: center; vertical-align: middle;"> <img class="cart-photo set-bg" style="width: 100px" src="' + orderDetails[i].productImg + '" > </td>'
        str += '<td style="vertical-align: middle;">'+orderDetails[i].quantity+'</td>'
        str += '</tr>'
    }
    document.getElementById("orderDetailQr").innerHTML = str
    document.getElementById("orderId").innerHTML = 'เลขที่ออร์เดอร์ : '+orderId;
    document.getElementById("amount").innerHTML = 'ยอดเงิน : '+amount;
}

function openItemcode(itemCode,orderDetailJson){
    const orderDetails = JSON.parse(orderDetailJson);
    let str = '';

    for(let i = 0; i < orderDetails.length ; i++){
        str += '<tr>'
        str += '<th scope="row" style="vertical-align: middle;">'+orderDetails[i].productName +'</th>'
        str += '<td style="text-align: center; vertical-align: middle;"> <img class="cart-photo set-bg" style="width: 100px" src="' + orderDetails[i].productImg + '" > </td>'
        str += '<td style="vertical-align: middle;">'+orderDetails[i].quantity+'</td>'
        str += '</tr>'
    }
    document.getElementById("orderDetail").innerHTML = str
    document.getElementById("itemCode").innerHTML = itemCode;
}
