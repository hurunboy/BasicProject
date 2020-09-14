
/**
 * 总页数@param（总条数，每页总条数）
 */
function pageTotal(rowCount, pageSize) {
    // console.log("总条数"+rowCount+"每页总条数"+pageSize)
    if (rowCount == null || rowCount == "") {
        return 0;
    } else {
        if (pageSize != 0 &&
            rowCount % pageSize == 0) {
            return parseInt(rowCount / pageSize);
        }
        if (pageSize != 0 &&
            rowCount % pageSize != 0) {
            return parseInt(rowCount / pageSize) + 1;
        }
    }
}

//日期格式转换
function formatDate(time) {
    if(time == null){
        return "-";
    }
    var now = new Date(time);
    var year=now.getFullYear();
    var month=now.getMonth()+1;
    var date=now.getDate();
    var hour=now.getHours();
    var minute=now.getMinutes();
    var second=now.getSeconds();
    return year+"-"+(month < 10?"0"+month:month)+"-"+(date < 10?"0"+date:date)+" "+(hour < 10?"0"+hour:hour)+":"+(minute<10?"0"+minute:minute)+":"+(second<10?"0"+second:second);
}

//处理为空
function forNull(str){
    return str == null ? "-":str;
}
//处理性别
function forGender(str){
    if(str == '1'){
        return '男';
    }else{
        return '女';
    }
}

//处理消息类型
function forKey(str){
    if(str == '1'){
        return '游戏公告';
    }else if(str == '2'){
        return '游戏下载';
    }else if(str == '3'){
        return '消息通知';
    }else{
        return '其它';
    }
}

//禁止点击
function btnF(obj){
    $(obj).attr('disabled',"true");
}
//允许点击
function btnT(obj){
    $(obj).removeAttr("disabled");
}
