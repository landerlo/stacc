cd img
ls *.dot | xargs -I{} basename {} .dot | xargs -I{} dot -Tpng {}.dot -o {}.png
cd ..
