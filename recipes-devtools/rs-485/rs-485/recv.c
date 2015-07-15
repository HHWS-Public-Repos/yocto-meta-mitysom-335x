#include "serial.h"
#include <stdio.h>
#include <stdlib.h>
#include <fcntl.h>
#include <termios.h>
#include <unistd.h>
#include <string.h>

/* Driver-specific ioctls: */
#define TIOCGRS485 0x542E
#define TIOCSRS485 0x542F

/* new defines in linux/serial.h of CL provided kernel */
#ifndef SER_RS485_USE_GPIO
#define SER_RS485_USE_GPIO (1<<5)
#define gpio_pin padding[1]
#endif

#define GPIO3_9 (32*3+9)
#define GPIO2_20 (32*2+20)

#ifdef UART3
#define TTY "/dev/ttyO3"
#define TX_ENABLE GPIO3_9
#else
#define TTY "/dev/ttyO4"
#define TX_ENABLE GPIO2_20
#endif

#define USE2

/*
 * Test program for 485 control
 */
int main(int argc, char* argv[])
{
	struct serial_rs485 rs485conf;
	char buffer2[21];
	int i, rv, opt;
	struct termios my_termios, new_termios, stdio;

	int fd2 = open(TTY, O_RDWR | O_NOCTTY | O_NDELAY);

	rs485conf.flags = (1<<0); //SER_RS485_ENABLED;
	rs485conf.flags |= SER_RS485_USE_GPIO;
	rs485conf.delay_rts_before_send = 0;
	rs485conf.gpio_pin = TX_ENABLE;

	rv = ioctl (fd2, TIOCSRS485, &rs485conf);
	if (rv) {
		perror("unable to set IOCTL:");
	}

	tcgetattr(fd2, &my_termios);
	my_termios.c_iflag &= ~(IGNBRK | BRKINT | ICRNL | INLCR | PARMRK | INPCK | ISTRIP | IXON);
	my_termios.c_oflag &= ~(OCRNL | ONLCR | ONLRET | ONOCR | OFILL | OLCUC | OPOST);
	my_termios.c_lflag &= ~(ECHO | ECHOE | ECHOKE | ECHONL | ICANON | IEXTEN | ISIG | ECHOK | ECHOCTL | ECHOPRT);
	my_termios.c_cflag &= ~(CSIZE | PARENB);
	my_termios.c_cflag &= ~CRTSCTS;
	my_termios.c_cflag |= CS8 | CLOCAL | CREAD;
	my_termios.c_cflag &= ~CBAUD;
	my_termios.c_cflag |= B115200; // B19200; // 
	my_termios.c_cc[VMIN] = 1;
	my_termios.c_cc[VTIME] = 5;
	rv = tcsetattr(fd2, TCSANOW, &my_termios);
	tcgetattr(fd2, &new_termios);

	fcntl(STDIN_FILENO, F_SETFL, O_NONBLOCK);

	printf("Reading (hit q, return to quit)...\n");
	while(1)
	{
		if(read(fd2, buffer2, 1) > 0) write(STDOUT_FILENO, buffer2, 1);
		if(read(STDIN_FILENO, buffer2, 1) > 0) {
			if (buffer2[0]='q') {
				break;
			}
		}
	}
	close(fd2);
}
