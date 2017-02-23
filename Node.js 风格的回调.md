大多数由 Node.js 核心 API 暴露出来的异步方法都遵循一个被称为“Node.js 风格的回调”的惯用模式。 使用这种模式，一个回调函数是作为一个参数传给方法的。 
当操作完成或发生错误时，回调函数会被调用，并带上错误对象（如果有）作为第一个参数。 如果没有发生错误，则第一个参数为 null。


const fs = require('fs');

function nodeStyleCallback(err, data) {
 if (err) {
   console.error('有一个错误', err);
   return;
 }
 console.log(data);
}

fs.readFile('/some/file/that/does-not-exist', nodeStyleCallback);
fs.readFile('/some/file/that/does-exist', nodeStyleCallback)
