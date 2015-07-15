# Assuming that you have CROSS_COMPILE defined iff you are not building under Yocto
ifdef CROSS_COMPILE
CC = $(CROSS_COMPILE)gcc
endif

CFLAGS = 

all:
	$(CC) $(CFLAGS) -DUART3 recv.c -o recv_uart3
	$(CC) $(CFLAGS) -DUART4 recv.c -o recv_uart4
	$(CC) $(CFLAGS) -DUART3 send.c -o send_uart3
	$(CC) $(CFLAGS) -DUART4 send.c -o send_uart4
	@file recv_uart3 recv_uart4 send_uart3 send_uart4

.PHONY : help
help:
	@echo "usage of this Makefile"
	@echo "make         : builds the application (same as 'make all')"
	@echo "make clean   : clean binaries"
	@echo "make release : makes a tar ball of these sources"
	@echo "make help    : this message"

.PHONY : clean
clean:
	-rm -rf recv_uart3 recv_uart4 send_uart3 send_uart4 *.o rs-485.tar.gz

.PHONY : release
release: clean
	tar czf rs-485.tar.gz *.c Makefile
