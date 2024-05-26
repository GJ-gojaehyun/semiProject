<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<link rel="icon" type="image/x-icon" href="<%=request.getContextPath()%>/images/logo.png">
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Page</title>
    <link rel="stylesheet" href="styles.css">
</head>
<style>
body {
    font-family: Arial, sans-serif;
    margin: 0;
    padding: 0;
    display: flex;
}

.sidebar {
    width: 250px;
    background-color: #333;
    color: #fff;
    height: 100vh;
    position: fixed;
    overflow-y: auto;
}

.sidebar h2 {
    text-align: center;
    padding: 0.3em;
    margin: 0;
    background-color: #444;
}
.sidebar button{
	background-color: #444;
	font-size: 15px;
    color: #fff;
    border: none;
    padding: 1em;
    cursor: pointer;
    width: 100%;
    text-align: center;
    position: absolute;
    bottom: 0;
    left: 0;
    transition: background-color 0.3s ease;
}
.sidebar button:hover{
	background-color: #847070;
}
.nav {
    list-style-type: none;
    padding: 0;
    margin: 0;
}

.nav li {
    border-bottom: 1px solid #444;
    position: relative;
}

.nav a {
    color: #fff;
    text-decoration: none;
    display: block;
    padding: 0.75em 1em;
}

.nav a:hover {
    background-color: #575757;
}

.sub-nav {
    max-height: 0;
    overflow: hidden;
    list-style-type: none;
    padding-left: 20px;
    transition: max-height 0.3s ease-out, padding 0.3s ease-out;
}

.nav li:hover .sub-nav {
    max-height: 500px;
    padding: 10px 0;
    transition: max-height 0.5s ease-in, padding 0.5s ease-in;
}

.sub-nav li {
    border: none;
}

.sub-nav a {
    padding: 0.5em 1em;
    background-color: #444;
}

.sub-nav a:hover {
    background-color: #575757;
}

.content {
    margin-left: 250px;
    padding: 2em;
    background-color: #bcbcbc;
    flex-grow: 1;
}

.content h1 {
    margin-top: 0;
}
</style>
<body>
    <div class="sidebar">
        <h2>��å�ϰ�</h2>
        <h2>Admin Page</h2>
        <ul class="nav">
            <li>
                <a href="#user-management">ȸ������</a>
                <ul class="sub-nav">
                    <li><a href="#user-view">ȸ�� ��ȸ</a></li>
                    <li><a href="#user-deletion">ȸ�� Ż��</a></li>
                </ul>
            </li>
            <li>
                <a href="#product-management">��ǰ����</a>
                <ul class="sub-nav">
                    <li><a href="#product-registration">��ǰ ���</a></li>
                    <li><a href="#product-edit">��ǰ ����</a></li>
                    <li><a href="#product-deletion">��ǰ ����</a></li>
                    <li><a href="#qna-management">QnA ����</a></li>
                </ul>
            </li>
            <li>
                <a href="#board-management">�Խ��� ����</a>
                <ul class="sub-nav">
                    <li><a href="#free-board">�����Խ���</a></li>
                    <li><a href="#notice-board">�������װԽ���</a></li>
                    <li><a href="#mungstagram-board">�۽�Ÿ�׷� �Խ���</a></li>
                    <li><a href="#walkmate-board">��å����Ʈ �Խ���</a></li>
                    <li><a href="#hotplace-board">���÷��̽� �Խ���</a></li>
                </ul>
            </li>
            <li>
                <a href="#order-management">�ֹ�����</a>
                <ul class="sub-nav">
                    <li><a href="#shipping-status">��ۻ��� ����</a></li>
                    <li><a href="#refund-cancellation">ȯ��/��� ����</a></li>
                </ul>
            </li>
        </ul>
        <button>�α׾ƿ�</button>
    </div>
    <div class="content">
        
    </div>
</body>
</html>