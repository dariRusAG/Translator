var i: integer;
begin

  for i := 1 to 5 do
  begin
    write(i);
    end;

    for i := 5 downto 1 do
      begin
        write(i);
      end;

  i := 1;
  while (i<5) do
  begin
    write(i);
    i += 1;
  end;

  i := 1;
  repeat
  begin
    write(i);
    i += 1;
    end;
  until (i>5);
end.
   
