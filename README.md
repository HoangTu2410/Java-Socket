# Java-Socket
- Lập trình java socket application
- Lập trình socket sử dụng TCP, UDP và jdbc
- Ứng dụng gồm 3 tầng Client, Server_TCP và Server_UDP: 
 + Client sẽ tương tác với người dùng, nhận dữ liệu đầu vào và gửi lên Server_TCP
 + Server_TCP sẽ nhận dữ liệu từ Client, đồng bộ giữa các Client và gửi dữ liệu lên Server_UDP
 + Server_UDP nhận dữ liệu từ Server_TCP và tương tác với cơ sở dữ liệu
- Ứng dụng gồm các chức năng:
 + Đăng nhập
 + Đăng kí tài khoản
 + Chỉnh sửa thông tin cá nhân
 + Tìm kiếm người dùng
 + Gửi lời mời kết bạn, xem danh sách bạn bè (có thể xem đang online hay offline), thêm bạn bè, xóa bạn bè
 + Xem bảng xếp hạng
 + Xem các nhóm đã tham gia, tìm kiếm nhóm, gửi yêu cầu tham gia nhóm, xem thành viên trong nhóm, mời bạn bè vào nhóm, xem các yêu cầu tham gia nhóm, rời nhóm, kích thành viên khỏi nhóm (nếu là admin)
 + Thách đấu bạn bè hoặc một người bất kì trên BXH nếu đang online
 + Khi chấp nhận thách đấu có thể chọn 1 nhân vật trong game Mario
- Ứng dụng được chạy trên: Netbean, MySQL
- Cách chạy ứng dụng:
1. Tải tất cả các file về máy
2. Import cơ sở dữ liệu vào MySQL Workbench từ folder database
3. Mở 4 project Entity, Client, Server_TCP, Server_UDP trong Netbean
4. Tạo file Entity.jar từ project Entity và import vào 3 project Client, Server_TCP, Server_UDP
5. Chỉnh sửa IPAddress ở 3 project Client, Server_TCP, Server_UDP cho tương thích
6. Chạy lần lượt từ Server_UDP -> Server_TCP -> Client (Có thể chạy đồng thời nhiều Client để tương tác với nhau)