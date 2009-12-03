{
  ok-sem04.s

  Testa se o compilador aceita redefinicao de nomes de variaveis globais
  e parametros.
  }
program ok04;
var a, b : integer;

procedure m( b, c : integer );
    var d : integer;
  begin
  d:= a + b + c;
  writeln(d)
  end

function p( a, b, c : integer ) : integer;
    var d : integer;
  begin
  d:= a*b;
  p:= a + b + c + d;
  end

begin
a:= 0;
b:= 0;
m(1, 2);
writeln( p(0,0,0) )
end.

